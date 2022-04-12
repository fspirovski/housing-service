package mk.ukim.finki.mpip.housing_service.ui.amenity_items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.AmenityItem
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
        val pendingAmenityItemsButton: Button = view.findViewById(R.id.pendingAmenityItemsButton)
        val paidAmenityItemsButton: Button = view.findViewById(R.id.paidAmenityItemsButton)
        val allAmenityItemsButton: Button = view.findViewById(R.id.allAmenityItemsButton)

        amenityItemsViewModel =
            ViewModelProvider(this)[AmenityItemsViewModel::class.java]
        amenityItemsRecyclerView = view.findViewById(R.id.amenityItemsRecyclerView)

        val amenityItemsAdapter =
            AmenityItemsAdapter(mutableListOf(), amenityItemsViewModel.isAdmin())
        amenityItemsAdapter.onItemClick = { openAmenityItemDetailsDialog(it) }

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

        pendingAmenityItemsButton.setOnClickListener {
            if (amenityItemsViewModel.isAdmin()) {
                amenityItemsViewModel.findAllAmenityItemsByStatus(AmenityItemStatus.PENDING)
            } else {
                amenityItemsViewModel.findAllAmenityItemsByResidentAndStatus(AmenityItemStatus.PENDING)
            }
        }

        paidAmenityItemsButton.setOnClickListener {
            if (amenityItemsViewModel.isAdmin()) {
                amenityItemsViewModel.findAllAmenityItemsByStatus(AmenityItemStatus.PAID)
            } else {
                amenityItemsViewModel.findAllAmenityItemsByResidentAndStatus(AmenityItemStatus.PAID)
            }
        }

        allAmenityItemsButton.setOnClickListener {
            if (amenityItemsViewModel.isAdmin()) {
                amenityItemsViewModel.findAllAmenityItemsByStatus(AmenityItemStatus.ALL)
            } else {
                amenityItemsViewModel.findAllAmenityItemsByResidentAndStatus(AmenityItemStatus.ALL)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (amenityItemsViewModel.isAdmin()) {
            amenityItemsViewModel.findAllAmenityItemsByStatus(AmenityItemStatus.PENDING)
        } else {
            amenityItemsViewModel.findAllAmenityItemsByResidentAndStatus(AmenityItemStatus.PENDING)
        }
    }

    private fun openAmenityItemDetailsDialog(amenityItem: AmenityItem) {
        val dialog = AmenityItemDetailsDialog(amenityItem)

        dialog.show(childFragmentManager, "Amenity item details dialog")
    }
}