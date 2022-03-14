package mk.ukim.finki.mpip.housing_service.ui.amenityItems

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.AmenityItem

class AmenityItemAdapter(var amenityItemsList: List<AmenityItem>) :
    RecyclerView.Adapter<AmenityItemAdapter.ViewHolder>() {

    var onItemClick: ((AmenityItem) -> Unit)? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.amenityItemID)
        val title: TextView = view.findViewById(R.id.amenityItemTitle)
        val amount: TextView = view.findViewById(R.id.amenityItemAmount)
        val status: TextView = view.findViewById(R.id.amenityItemStatus)

        init {
            view.setOnClickListener {
                onItemClick?.invoke(amenityItemsList[adapterPosition])
            }
        }

        fun populateViewHolder(amenityItem: AmenityItem) {
            id.text = amenityItem.id
            title.text = amenityItem.amenity.title
            amount.text = amenityItem.amenity.amount
            status.text = amenityItem.status.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.amenity_item_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentAmenity = amenityItemsList[position]

        holder.populateViewHolder(currentAmenity)
    }

    override fun getItemCount(): Int {
        return amenityItemsList.size
    }

    fun updateAmenityItems(amenityItems: List<AmenityItem>) {
        amenityItemsList = amenityItems
        this.notifyDataSetChanged()
    }
}