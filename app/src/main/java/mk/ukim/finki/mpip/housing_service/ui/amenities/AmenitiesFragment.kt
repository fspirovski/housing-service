package mk.ukim.finki.mpip.housing_service.ui.amenities

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.Amenity
import mk.ukim.finki.mpip.housing_service.domain.model.AmenityStatus
import mk.ukim.finki.mpip.housing_service.domain.model.HouseCouncil
import mk.ukim.finki.mpip.housing_service.ui.amenities.adapter.AmenityAdapter
import java.time.LocalDateTime

class AmenitiesFragment : Fragment() {

    private lateinit var amenitiesViewModel: AmenitiesViewModel
    private lateinit var amenitiesRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        amenitiesViewModel =
            ViewModelProvider(this).get(AmenitiesViewModel::class.java)

        return inflater.inflate(R.layout.fragment_amenities, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        amenitiesRecyclerView = view.findViewById(R.id.amenitiesRecyclerView)
        amenitiesRecyclerView.layoutManager = LinearLayoutManager(activity)
        amenitiesRecyclerView.setHasFixedSize(true)
        amenitiesRecyclerView.adapter = AmenityAdapter(mutableListOf())

        amenitiesViewModel.findAllAmenitiesByHouseCouncil()

        val list = mutableListOf<Amenity>()
        list.add(
            Amenity(
                "1",
                "Title",
                "Desc",
                "2000",
                LocalDateTime.now(),
                AmenityStatus.APPROVED,
            )
        )

    }
}