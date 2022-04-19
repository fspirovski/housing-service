package mk.ukim.finki.mpip.housing_service.ui.amenities

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.text.bold
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.Amenity

class AmenityDetailsDialog(val amenity: Amenity) : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity?.layoutInflater?.inflate(R.layout.amenity_details_dialog, null)

        val title: TextView? = view?.findViewById(R.id.amenityDetailsTitle)
        val description: TextView? = view?.findViewById(R.id.amenityDetailsDescription)
        val amount: TextView? = view?.findViewById(R.id.amenityDetailsAmount)
        val status: TextView? = view?.findViewById(R.id.amenityDetailsStatus)

        title?.text = amenity.title
        description?.text = SpannableStringBuilder()
            .bold { append("Description:") }
            .append(" ${amenity.description}")
        amount?.text = SpannableStringBuilder()
            .bold { append("Amount to pay:") }
            .append(" ${amenity.amount}")
        status?.text = SpannableStringBuilder()
            .bold { append("Current status:") }
            .append(" ${amenity.status}")

        return builder.setView(view)
            .setPositiveButton("OK") { _, _ -> }
            .create()
    }
}