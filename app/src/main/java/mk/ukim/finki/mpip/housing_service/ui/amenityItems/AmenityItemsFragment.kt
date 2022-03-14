package mk.ukim.finki.mpip.housing_service.ui.amenityItems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.AmenityItemStatus

class AmenityItemsFragment : Fragment() {

    private lateinit var amenityItemsViewModel: AmenityItemsViewModel
    private lateinit var amenityItemsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_amenity_items, container, false)

        amenityItemsViewModel =
            ViewModelProvider(this)[AmenityItemsViewModel::class.java]
        amenityItemsRecyclerView = view.findViewById(R.id.amenityItemsRecyclerView)

        val amenityItemsAdapter = AmenityItemAdapter(mutableListOf())
        amenityItemsAdapter.onItemClick = {
            AmenityItemsFragmentDirections.actionAmenityItemsToAmenityItemDetailsFragment(it)
        }

        amenityItemsRecyclerView.adapter = amenityItemsAdapter
        amenityItemsRecyclerView.layoutManager = LinearLayoutManager(activity)
        amenityItemsRecyclerView.setHasFixedSize(true)

        amenityItemsViewModel.responseMessage.observe(viewLifecycleOwner, {
            Toast
                .makeText(activity, it, Toast.LENGTH_LONG)
                .show()
        })
        amenityItemsViewModel.amenityItemsList.observe(viewLifecycleOwner, {
            amenityItemsAdapter.updateAmenityItems(it)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        amenityItemsViewModel.findAllAmenityItemsByResidentAndStatus(AmenityItemStatus.PENDING)
    }

}