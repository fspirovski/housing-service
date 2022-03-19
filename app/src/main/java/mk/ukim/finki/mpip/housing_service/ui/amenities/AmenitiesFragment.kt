package mk.ukim.finki.mpip.housing_service.ui.amenities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.Amenity

class AmenitiesFragment : Fragment() {

    private lateinit var amenitiesViewModel: AmenitiesViewModel
    private lateinit var amenitiesRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_amenities, container, false)

        amenitiesViewModel = ViewModelProvider(this)[AmenitiesViewModel::class.java]
        amenitiesRecyclerView = view.findViewById(R.id.amenitiesRecyclerView)

        val amenitiesAdapter = AmenitiesAdapter(mutableListOf())
        amenitiesAdapter.onItemClick = { amenity -> openDialog(amenity) }

        amenitiesRecyclerView.adapter = amenitiesAdapter
        amenitiesRecyclerView.layoutManager = LinearLayoutManager(activity)
        amenitiesRecyclerView.setHasFixedSize(true)

        amenitiesViewModel.responseMessage.observe(viewLifecycleOwner, {
            Toast
                .makeText(activity, it, Toast.LENGTH_LONG)
                .show()
        })
        amenitiesViewModel.amenitiesList.observe(viewLifecycleOwner, {
            amenitiesAdapter.updateAmenities(it)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        amenitiesViewModel.findAllAmenitiesByHouseCouncil()
    }

    private fun openDialog(amenity: Amenity) {
        val dialog = AmenityDialog(amenity)

        dialog.show(childFragmentManager, "Amenity details dialog")
    }
}