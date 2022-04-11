package mk.ukim.finki.mpip.housing_service.ui.amenity_items

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.OpenableColumns
import android.text.SpannableStringBuilder
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.text.bold
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.storage.FirebaseStorage
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.dto.ConfirmationOfPaymentDto
import mk.ukim.finki.mpip.housing_service.domain.model.AmenityItem
import mk.ukim.finki.mpip.housing_service.domain.model.AmenityItemStatus
import java.io.File

class AmenityItemDetailsDialog(val amenityItem: AmenityItem) : AppCompatDialogFragment() {

    interface AmenityItemDetailsDialogListener {
        fun saveConfirmationOfPayment(URL: String)
    }

    private lateinit var amenityItemsViewModel: AmenityItemsViewModel
    private var firebaseStorageInstance = FirebaseStorage.getInstance()
    private var storageReference = firebaseStorageInstance.reference
    private lateinit var uploadFileLauncher: ActivityResultLauncher<Intent>
    private lateinit var amenityItemDetailsDialogListener: AmenityItemDetailsDialogListener

    fun setAmenityItemDetailsDialogListener(amenityItemDetailsDialogListener: AmenityItemDetailsDialogListener) {
        this.amenityItemDetailsDialogListener = amenityItemDetailsDialogListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        amenityItemsViewModel = ViewModelProvider(this)[AmenityItemsViewModel::class.java]
        amenityItemsViewModel.responseMessage.observe(this, {
            Toast
                .makeText(context, it, Toast.LENGTH_LONG)
                .show()
        })

        val builder = AlertDialog.Builder(activity)
        val view = activity?.layoutInflater?.inflate(R.layout.amenity_item_details_dialog, null)

        val title: TextView? = view?.findViewById(R.id.amenityItemDetailsTitle)
        val description: TextView? = view?.findViewById(R.id.amenityItemDetailsDescription)
        val amount: TextView? = view?.findViewById(R.id.amenityItemDetailsAmount)
        val status: TextView? = view?.findViewById(R.id.amenityItemDetailsStatus)
        val confirmationOfPayment: TextView? =
            view?.findViewById(R.id.amenityItemDetailsConfirmationOfPayment)
        val uploadConfirmationOfPaymentButton: Button? =
            view?.findViewById(R.id.uploadConfirmationOfPaymentButton)

        title?.text = amenityItem.amenity.title
        description?.text = SpannableStringBuilder()
            .bold { append("Description:") }
            .append(" ${amenityItem.amenity.description}")
        amount?.text = SpannableStringBuilder()
            .bold {
                append(
                    "${
                        if (amenityItem.status == AmenityItemStatus.PENDING)
                            "Amount to pay"
                        else
                            "Paid amount"
                    }:"
                )
            }
            .append(" ${amenityItem.amenity.amount}")
        status?.text = SpannableStringBuilder()
            .bold { append("Your status:") }
            .append(" ${amenityItem.status}")
        confirmationOfPayment?.text = SpannableStringBuilder()
            .append(" ${amenityItem.confirmationOfPaymentFileName ?: "No confirmation attached."}")
        uploadConfirmationOfPaymentButton?.setOnClickListener { uploadPDFFile() }

        if (amenityItem.confirmationOfPaymentUri != null) {
            confirmationOfPayment?.setOnClickListener {
                val externalFileReference =
                    firebaseStorageInstance.getReferenceFromUrl(amenityItem.confirmationOfPaymentUri)
                val parent =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val localFile =
                    File.createTempFile(amenityItem.confirmationOfPaymentFileName, ".pdf", parent)

                externalFileReference.getFile(localFile)
                    .addOnSuccessListener {
                        Toast.makeText(this.context, "Download complete.", Toast.LENGTH_LONG).show()
                    }.addOnFailureListener {
                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                    }
            }
        }

        if (amenityItem.status == AmenityItemStatus.PAID) {
            uploadConfirmationOfPaymentButton?.isEnabled = false
        }

        uploadFileLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data = result.data

                    if (data != null) {
                        val progressDialog = ProgressDialog(this.context)

                        progressDialog.setMessage("File upload started.")
                        progressDialog.setCancelable(false)
                        progressDialog.show()

                        val uri = data.data!!
                        val filePath = data.dataString!!
                        val localFileName = getLocalFileName(uri)
                            ?: filePath.substring(filePath.lastIndexOf('/') + 1)
                        val firebaseFileName = "${System.currentTimeMillis()}_$localFileName"

                        storageReference.child(firebaseFileName)
                            .putFile(uri)
                            .addOnSuccessListener { taskSnapshot ->
                                val task = taskSnapshot.storage.downloadUrl

                                while (!task.isComplete) {
                                }

                                val downloadUri = task.result!!

                                amenityItemsViewModel.responseError.observe(this, {
                                    if (!it) {
                                        confirmationOfPayment?.text = localFileName
                                        Toast.makeText(
                                            this.context,
                                            "Successful upload.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                })
                                amenityItemsViewModel.sendConfirmationOfPayment(
                                    amenityItem.id,
                                    ConfirmationOfPaymentDto(localFileName, downloadUri.toString())
                                )

                                progressDialog.dismiss()
                            }
                            .addOnProgressListener {
                                val progress = (it.bytesTransferred / it.totalByteCount) * 100.0
                                progressDialog.setMessage("File uploading: ${progress.toInt()}%")
                            }
                            .addOnFailureListener {
                                progressDialog.dismiss()
                                Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                            }
                    } else {
                        Toast.makeText(this.context, "Action failed.", Toast.LENGTH_LONG).show()
                    }
                }
            }

        return builder.setView(view)
            .setNegativeButton("OK") { _, _ -> }
            .create()
    }

    private fun uploadPDFFile() {
        val intent = Intent()

        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "application/pdf"

        uploadFileLauncher.launch(intent)
    }

    private fun getLocalFileName(uri: Uri): String? {
        val scheme: String? = uri.scheme

        if (ContentResolver.SCHEME_FILE == scheme) {
            return uri.lastPathSegment!!
        } else if (ContentResolver.SCHEME_CONTENT == scheme) {
            val projection = arrayOf(OpenableColumns.DISPLAY_NAME)
            var cursor: Cursor? = null

            try {
                cursor = context!!.contentResolver
                    .query(uri, projection, null, null, null)

                if (cursor != null && cursor.moveToFirst()) {
                    return cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                }

            } catch (e: IllegalArgumentException) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            } finally {
                cursor?.close()
            }
        }

        return null
    }
}