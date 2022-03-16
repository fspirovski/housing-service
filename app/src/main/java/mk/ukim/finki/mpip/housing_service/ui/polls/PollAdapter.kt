package mk.ukim.finki.mpip.housing_service.ui.polls

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.AmenityItem
import mk.ukim.finki.mpip.housing_service.domain.model.Poll

class PollAdapter(var pollsList: List<Poll>) : RecyclerView.Adapter<PollAdapter.ViewHolder>() {

    var onItemClick: ((Poll) -> Unit)? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.pollId)
        val adminCandidate: TextView = view.findViewById(R.id.adminCandidate)
        val amenityTitle: TextView = view.findViewById(R.id.amenityCandidate)

        init {
            view.setOnClickListener {
                onItemClick?.invoke(pollsList[adapterPosition])
            }
        }

        fun populateViewHolder(poll: Poll) {
            id.text = poll.id
            adminCandidate.text = poll.adminCandidate?.name ?: ""
            amenityTitle.text = poll.amenityCandidate?.title ?: ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.poll_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPoll = pollsList[position]

        holder.populateViewHolder(currentPoll)
    }

    override fun getItemCount(): Int {
        return pollsList.size
    }

    fun updatePolls(polls: List<Poll>) {
        pollsList = polls
        notifyDataSetChanged()
    }
}