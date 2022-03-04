package mk.ukim.finki.mpip.housing_service.ui.amenityItems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.ui.amenityItems.adapter.AmenityItemAdapter

class AmenityItemsFragment : Fragment() {

    private lateinit var amenityItemsViewModel: AmenityItemsViewModel
    private lateinit var amenityItemsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        amenityItemsViewModel =
            ViewModelProvider(this).get(AmenityItemsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_amenity_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        amenityItemsRecyclerView = view.findViewById(R.id.amenityItemsRecyclerView)
        amenityItemsRecyclerView.layoutManager = LinearLayoutManager(activity)
        amenityItemsRecyclerView.setHasFixedSize(true)

        amenityItemsRecyclerView.adapter = AmenityItemAdapter(mutableListOf())

        amenityItemsViewModel.findAllAmenityItemsByResidentAndStatus(null)
    }

}