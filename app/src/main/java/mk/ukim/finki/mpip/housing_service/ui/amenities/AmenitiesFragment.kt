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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.Amenity

class AmenitiesFragment : Fragment(), NewAmenityDialog.NewAmenityDialogListener {

    private lateinit var amenitiesViewModel: AmenitiesViewModel
    private lateinit var amenitiesRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_amenities, container, false)
        val newAmenityButton: FloatingActionButton = view.findViewById(R.id.newAmenityButton)

        amenitiesViewModel = ViewModelProvider(this)[AmenitiesViewModel::class.java]
        amenitiesRecyclerView = view.findViewById(R.id.amenitiesRecyclerView)

        val amenitiesAdapter = AmenitiesAdapter(mutableListOf())
        amenitiesAdapter.onItemClick = { openAmenityDetailsDialog(it) }

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

        newAmenityButton.setOnClickListener { openNewAmenityDialog() }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        amenitiesViewModel.findAllAmenitiesByHouseCouncil()
    }

    private fun openAmenityDetailsDialog(amenity: Amenity) {
        val dialog = AmenityDetailsDialog(amenity)

        dialog.show(childFragmentManager, "Amenity details dialog")
    }

    private fun openNewAmenityDialog() {
        val dialog = NewAmenityDialog()

        dialog.setNewAmenityDialogListener(this)
        dialog.show(childFragmentManager, "New amenity dialog")
    }

    override fun saveUserInput(title: String, description: String, amount: Double) {
        amenitiesViewModel.createAmenity(title, description, amount)
    }
}