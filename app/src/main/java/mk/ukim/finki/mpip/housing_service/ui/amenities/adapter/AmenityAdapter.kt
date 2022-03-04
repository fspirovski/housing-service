package mk.ukim.finki.mpip.housing_service.ui.amenities.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.Amenity

class AmenityAdapter(var amenities: MutableList<Amenity>) :
    RecyclerView.Adapter<AmenityAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView
        val title: TextView
        val description: TextView
        val amount: TextView

        init {
            id = view.findViewById(R.id.amenityID)
            title = view.findViewById(R.id.amenityTitle)
            description = view.findViewById(R.id.amenityDescription)
            amount = view.findViewById(R.id.amenityAmount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.amenity_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentAmenity = amenities[position]

        holder.id.text = currentAmenity.id.toString()
        holder.title.text = currentAmenity.title.toString()
        holder.description.text = currentAmenity.description.toString()
        holder.amount.text = currentAmenity.amount.toString()
    }

    override fun getItemCount(): Int {
        return amenities.size
    }
}