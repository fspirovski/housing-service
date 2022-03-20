package mk.ukim.finki.mpip.housing_service.ui.amenities

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.Amenity

class AmenityDialog(val amenity: Amenity) : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(activity)
        val view = activity?.layoutInflater?.inflate(R.layout.amenity_dialog, null)

        val title: TextView? = view?.findViewById(R.id.amenityDetailsTitle)
        val description: TextView? = view?.findViewById(R.id.amenityDetailsDescription)
        val amount: TextView? = view?.findViewById(R.id.amenityDetailsAmount)
        val status: TextView? = view?.findViewById(R.id.amenityDetailsStatus)

        title?.text = amenity.title
        description?.text = "Description: \"${amenity.description}\""
        amount?.text = "Amount to pay: ${amenity.amount}"
        status?.text = "Current status: ${amenity.status}"

        dialog.setView(view)
        dialog.setPositiveButton("Close") { _, _ -> }

        return dialog.create()
    }
}