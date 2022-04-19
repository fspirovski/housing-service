package mk.ukim.finki.mpip.housing_service.ui.polls

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.*
import mk.ukim.finki.mpip.housing_service.service.LocalStorageService

class PollAdapter(var pollsList: List<Poll>) : RecyclerView.Adapter<PollAdapter.ViewHolder>() {

    var onItemClick: ((Poll) -> Unit)? = null
    private val localStorageService = LocalStorageService()


    inner class ViewHolder(view: View, private val images: IntArray) : RecyclerView.ViewHolder(view) {
        val pollTitle: TextView = view.findViewById(R.id.pollTitle)
//        val adminCandidate: TextView = view.findViewById(R.id.adminCandidate)
//        val amenityTitle: TextView = view.findViewById(R.id.amenityCandidate)
        private val upVoteArrow: ImageView = view.findViewById(R.id.upVoteArrow)
        private val downVoteArrow: ImageView = view.findViewById(R.id.downVoteArrow)
        private val votesForPercentage: TextView = view.findViewById(R.id.votesForPercentage)
        private val votesAgainstPercentage: TextView = view.findViewById(R.id.votesAgainstPercentage)

        init {
            view.setOnClickListener {
                onItemClick?.invoke(pollsList[adapterPosition])
            }
        }

        fun populateViewHolder(poll: Poll) {
            val currentUserId = localStorageService.getData("current-user-id", "").toString()
            val currentVote: Vote? = poll.votes.find { vote: Vote -> vote.voter!!.id == currentUserId }

            if(poll.adminCandidate != null) {
                pollTitle.text = "Admin Poll - ${poll.adminCandidate.name} ${poll.adminCandidate.surname}"
            } else if(poll.amenityCandidate != null) {
                pollTitle.text = "Amenity Poll - ${poll.amenityCandidate.title}"
            }

            val upVoteArrowImage = when (currentVote?.status) {
                VoteStatus.FOR -> images[2]
                VoteStatus.AGAINST -> images[0]
                VoteStatus.PENDING -> images[0]
                else -> images[0]
            }

            val downVoteArrowImage = when (currentVote?.status) {
                VoteStatus.FOR -> images[1]
                VoteStatus.AGAINST -> images[3]
                VoteStatus.PENDING -> images[1]
                else -> images[1]
            }

            upVoteArrow.setImageResource(upVoteArrowImage)
            downVoteArrow.setImageResource(downVoteArrowImage)
            votesForPercentage.setTextColor(Color.parseColor("#6BCB77"))
            votesAgainstPercentage.setTextColor(Color.parseColor("#FF6B6B"))
            votesForPercentage.text = "${(poll.votes.count { vote: Vote -> vote.status == VoteStatus.FOR } / poll.votes.count())*100}%"
            votesAgainstPercentage.text = "${(poll.votes.count { vote: Vote -> vote.status == VoteStatus.AGAINST } / poll.votes.count())*100}%"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.poll_row, parent, false)
        val images = intArrayOf(R.drawable.up_arrow, R.drawable.down_arrow, R.drawable.voted_for, R.drawable.voted_against)

        return ViewHolder(view, images)
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
        this.notifyDataSetChanged()
    }
}