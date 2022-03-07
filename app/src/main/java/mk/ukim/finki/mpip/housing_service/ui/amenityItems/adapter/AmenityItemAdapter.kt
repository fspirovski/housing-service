package mk.ukim.finki.mpip.housing_service.ui.amenityItems.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.AmenityItem
import mk.ukim.finki.mpip.housing_service.ui.amenities.adapter.AmenityAdapter

class AmenityItemAdapter(var amenityItems: MutableList<AmenityItem>) :
    RecyclerView.Adapter<AmenityItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView
        val title: TextView
        val amount: TextView
        val status: TextView

        init {
            id = view.findViewById(R.id.amenityItemID)
            title = view.findViewById(R.id.amenityItemTitle)
            amount = view.findViewById(R.id.amenityItemAmount)
            status = view.findViewById(R.id.amenityItemStatus)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.amenity_item_row, parent, false)

        return AmenityItemAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentAmenity = amenityItems[position]

        holder.id.text = currentAmenity.id.toString()
//        holder.title.text = currentAmenity.title.toString()
        holder.status.text = currentAmenity.status.toString()
//        holder.amount.text = currentAmenity.amount.toString()
    }

    override fun getItemCount(): Int {
        return amenityItems.size
    }
}