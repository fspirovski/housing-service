package mk.ukim.finki.mpip.housing_service.ui.polls

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.Poll
import mk.ukim.finki.mpip.housing_service.domain.model.Vote
import mk.ukim.finki.mpip.housing_service.domain.model.VoteStatus
import mk.ukim.finki.mpip.housing_service.service.LocalStorageService

class VoteDialog(val poll: Poll) : AppCompatDialogFragment() {

    interface VoteDialogListener {
        fun saveVote(adminCandidateId: VoteStatus, pollId: String)
    }

    private val localStorageService = LocalStorageService()
    private lateinit var voteDialogListener: VoteDialogListener

    fun setVoteDialogListener(voteDialogListener: VoteDialogListener) {
        this.voteDialogListener = voteDialogListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity?.layoutInflater?.inflate(R.layout.vote_dialog, null)
        val currentVote: Vote? = poll.votes.find { vote: Vote ->
            vote.voter?.id == localStorageService.getData(
                "current-user-id",
                ""
            ).toString()
        }

        val voteForButton: RadioButton? = view?.findViewById(R.id.radio_for)
        val voteAgainstButton: RadioButton? = view?.findViewById(R.id.radio_against)

        when (currentVote?.status) {
            VoteStatus.FOR -> voteForButton?.isChecked = true
            VoteStatus.AGAINST -> voteAgainstButton?.isChecked = true
            else -> {
            }
        }

        return builder.setView(view)
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Save") { _, _ ->
                val radioGroup = view!!.findViewById<RadioGroup>(R.id.radio_group)
                if (radioGroup.checkedRadioButtonId != -1) {
                    val radioButton: RadioButton =
                        view.findViewById(radioGroup.checkedRadioButtonId)

                    voteDialogListener.saveVote(
                        VoteStatus.valueOf(radioButton.text.toString()),
                        poll.id
                    )
                } else {
                    Toast
                        .makeText(activity, "Please enter your vote", Toast.LENGTH_LONG)
                        .show()
                }
            }
            .create()
    }
}