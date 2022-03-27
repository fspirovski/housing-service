package mk.ukim.finki.mpip.housing_service.ui.amenities

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import mk.ukim.finki.mpip.housing_service.R

class NewAmenityDialog : AppCompatDialogFragment() {

    interface NewAmenityDialogListener {
        fun saveUserInput(title: String, description: String, amount: Double)
    }

    private lateinit var newAmenityDialogListener: NewAmenityDialogListener

    fun setNewAmenityDialogListener(newAmenityDialogListener: NewAmenityDialogListener) {
        this.newAmenityDialogListener = newAmenityDialogListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity?.layoutInflater?.inflate(R.layout.new_amenity_dialog, null)

        val title: EditText? = view?.findViewById(R.id.newAmenityTitle)
        val description: EditText? = view?.findViewById(R.id.newAmenityDescription)
        val amount: EditText? = view?.findViewById(R.id.newAmenityAmount)

        return builder.setView(view)
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Save") { _, _ ->
                val t = title?.text.toString().trim()
                val d = description?.text.toString().trim()
                val a = amount?.text.toString().trim()

                val validationResult = validateUserInput(t, d, a)

                if (validationResult.containsKey(true)) {
                    newAmenityDialogListener.saveUserInput(t, d, a.toDouble())
                } else {
                    Toast
                        .makeText(activity, validationResult[false], Toast.LENGTH_LONG)
                        .show()
                }
            }
            .create()
    }

    private fun validateUserInput(
        title: String,
        description: String,
        amount: String
    ): Map<Boolean, String> {
        if (title.isEmpty() || title.isBlank()) {
            return mapOf(false to "The 'title' field is required.")
        }

        if (description.isEmpty() || description.isBlank()) {
            return mapOf(false to "The 'description' field is required.")
        }

        if (amount.isEmpty() || amount.isBlank()) {
            return mapOf(false to "The 'amount' field is required.")
        }

        if (!amount.matches(Regex("[1-9][0-9]*"))) {
            return mapOf(false to "The 'amount' field must contain a positive value.")
        }

        return mapOf(true to "Valid user input.")
    }
}