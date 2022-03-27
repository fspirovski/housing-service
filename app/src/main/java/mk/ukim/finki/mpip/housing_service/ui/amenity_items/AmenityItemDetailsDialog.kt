package mk.ukim.finki.mpip.housing_service.ui.amenity_items

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.AmenityItem

class AmenityItemDetailsDialog(val amenityItem: AmenityItem) : AppCompatDialogFragment() {

    interface AmenityItemDetailsDialogListener {
        fun saveConfirmationOfPayment(URL: String)
    }

    private lateinit var amenityItemDetailsDialogListener: AmenityItemDetailsDialogListener

    fun setAmenityItemDetailsDialogListener(amenityItemDetailsDialogListener: AmenityItemDetailsDialogListener) {
        this.amenityItemDetailsDialogListener = amenityItemDetailsDialogListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity?.layoutInflater?.inflate(R.layout.amenity_item_details_dialog, null)

        val title: TextView? = view?.findViewById(R.id.amenityItemDetailsTitle)
        val description: TextView? = view?.findViewById(R.id.amenityItemDetailsDescription)
        val amount: TextView? = view?.findViewById(R.id.amenityItemDetailsAmount)
        val status: TextView? = view?.findViewById(R.id.amenityItemDetailsStatus)

        title?.text = amenityItem.amenity.title
        description?.text = amenityItem.amenity.description
        amount?.text = "Amount to pay: ${amenityItem.amenity.amount}"
        status?.text = "Current status: ${amenityItem.status}"

        return builder.setView(view)
            .setNegativeButton("OK") { _, _ -> }
            .setPositiveButton("Save") { _, _ -> }
            .create()
    }
}