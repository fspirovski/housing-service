package mk.ukim.finki.mpip.housing_service.ui.amenityItemDetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import mk.ukim.finki.mpip.housing_service.R

class AmenityItemDetailsFragment : Fragment() {

    private lateinit var amenityItemDetailsViewModel: AmenityItemDetailsViewModel
    val args: AmenityItemDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        amenityItemDetailsViewModel =
            ViewModelProvider(this).get(AmenityItemDetailsViewModel::class.java)

        return inflater.inflate(R.layout.amenity_item_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailsAmenityItemId: TextView = view.findViewById(R.id.detailsAmenityItemId)
        val detailsAmenityItemTitle: TextView = view.findViewById(R.id.detailsAmenityItemTitle)
        val detailsAmenityItemDescription: TextView = view.findViewById(R.id.detailsAmenityItemDescription)
        val detailsAmenityItemAmount: TextView = view.findViewById(R.id.detailsAmenityItemAmount)
        val detailsAmenityItemStatus: TextView = view.findViewById(R.id.detailsAmenityItemStatus)

        detailsAmenityItemId.text = args.amenityItem!!.id
        detailsAmenityItemTitle.text = args.amenityItem!!.amenity.title
        detailsAmenityItemDescription.text = args.amenityItem!!.amenity.description
        detailsAmenityItemAmount.text = args.amenityItem!!.amenity.amount
        detailsAmenityItemStatus.text = args.amenityItem!!.status.toString()
    }

}