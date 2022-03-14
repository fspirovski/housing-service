package mk.ukim.finki.mpip.housing_service.ui.amenities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.Amenity

class AmenitiesAdapter(var amenitiesList: List<Amenity>) :
    RecyclerView.Adapter<AmenitiesAdapter.ViewHolder>() {

    var onItemClick: ((Amenity) -> Unit)? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val id: TextView = view.findViewById(R.id.amenityID)
        private val title: TextView = view.findViewById(R.id.amenityTitle)
        private val description: TextView = view.findViewById(R.id.amenityDescription)
        private val amount: TextView = view.findViewById(R.id.amenityAmount)

        init {
            view.setOnClickListener {
                onItemClick?.invoke(amenitiesList[adapterPosition])
            }
        }

        fun populateViewHolder(amenity: Amenity) {
            id.text = amenity.id
            title.text = amenity.title
            description.text = amenity.description
            amount.text = amenity.amount
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.amenity_row, parent, false)

        return ViewHolder(view)
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