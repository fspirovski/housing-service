package mk.ukim.finki.mpip.housing_service.ui.polls.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.Poll

class PollAdapter(var polls: MutableList<Poll>) : RecyclerView.Adapter<PollAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView
        val adminCandidate: TextView
        val amenityTitle: TextView

        init {
            id = view.findViewById(R.id.pollId)
            adminCandidate = view.findViewById(R.id.adminCandidate)
            amenityTitle = view.findViewById(R.id.amenityCandidate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.poll_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPoll = polls[position]

        holder.id.text = currentPoll.id.toString()
        holder.adminCandidate.text = currentPoll.adminCandidate.toString()
        holder.amenityTitle.text = currentPoll.amenityCandidate.toString()
    }

    override fun getItemCount(): Int {
        return polls.size
    }
}