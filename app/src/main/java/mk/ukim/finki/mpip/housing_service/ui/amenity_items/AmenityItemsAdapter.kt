package mk.ukim.finki.mpip.housing_service.ui.amenity_items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.AmenityItem
import mk.ukim.finki.mpip.housing_service.domain.model.AmenityItemStatus

class AmenityItemsAdapter(var amenityItemsList: List<AmenityItem>) :
    RecyclerView.Adapter<AmenityItemsAdapter.ViewHolder>() {

    var onItemClick: ((AmenityItem) -> Unit)? = null

    inner class ViewHolder(view: View, private val images: IntArray) :
        RecyclerView.ViewHolder(view) {
        private val amenityItemTitle: TextView = view.findViewById(R.id.amenityItemTitle)
        private val amenityItemStatus: ImageView = view.findViewById(R.id.amenityItemStatus)

        init {
            view.setOnClickListener {
                onItemClick?.invoke(amenityItemsList[adapterPosition])
            }
        }

        fun populateViewHolder(amenityItem: AmenityItem) {
            val title = "${adapterPosition + 1}. ${amenityItem.amenity.title}"
            val status = when (amenityItem.status) {
                AmenityItemStatus.PENDING -> images[0]
                AmenityItemStatus.PAID -> images[1]
            }

            amenityItemTitle.text = title
            amenityItemStatus.setImageResource(status)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.amenity_item_row, parent, false)
        val images = intArrayOf(R.drawable.pending, R.drawable.paid)

        return ViewHolder(view, images)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val amenityItem = amenityItemsList[position]

        holder.populateViewHolder(amenityItem)
    }

    override fun getItemCount(): Int {
        return amenityItemsList.size
    }

    fun updateAmenityItems(amenityItems: List<AmenityItem>) {
        amenityItemsList = amenityItems
        this.notifyDataSetChanged()
    }
}