package mk.ukim.finki.mpip.housing_service.ui.amenities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.Amenity
import mk.ukim.finki.mpip.housing_service.domain.model.AmenityStatus

class AmenitiesAdapter(var amenitiesList: List<Amenity>) :
    RecyclerView.Adapter<AmenitiesAdapter.ViewHolder>() {

    var onItemClick: ((Amenity) -> Unit)? = null

    inner class ViewHolder(view: View, private val images: IntArray) :
        RecyclerView.ViewHolder(view) {
        private val amenityTitle: TextView = view.findViewById(R.id.amenityTitle)
        private val amenityDescription: TextView = view.findViewById(R.id.amenityDescription)
        private val amenityStatus: ImageView = view.findViewById(R.id.amenityStatus)

        init {
            view.setOnClickListener {
                onItemClick?.invoke(amenitiesList[adapterPosition])
            }
        }

        fun populateViewHolder(amenity: Amenity) {
            val title = "${adapterPosition + 1}. ${amenity.title}"
            val description = "${amenity.description}"
            val status = when (amenity.status) {
                AmenityStatus.PENDING -> images[0]
                AmenityStatus.APPROVED -> images[1]
                AmenityStatus.REJECTED -> images[2]
            }

            amenityTitle.text = title
            amenityDescription.text = description
            amenityStatus.setImageResource(status)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.amenity_row, parent, false)
        val images = intArrayOf(R.drawable.pending, R.drawable.approved, R.drawable.rejected)

        return ViewHolder(view, images)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val amenity = amenitiesList[position]

        holder.populateViewHolder(amenity)
    }

    override fun getItemCount(): Int {
        return amenitiesList.size
    }

    fun updateAmenities(amenities: List<Amenity>) {
        amenitiesList = amenities
        this.notifyDataSetChanged()
    }
}