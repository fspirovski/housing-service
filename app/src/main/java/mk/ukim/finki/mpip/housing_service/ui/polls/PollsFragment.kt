package mk.ukim.finki.mpip.housing_service.ui.polls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.HouseCouncil
import mk.ukim.finki.mpip.housing_service.domain.model.Poll
import mk.ukim.finki.mpip.housing_service.domain.model.VoteStatus
import mk.ukim.finki.mpip.housing_service.service.LocalStorageService

class PollsFragment : Fragment(), NewPollDialog.NewPollDialogListener,
    VoteDialog.VoteDialogListener {

    private lateinit var pollsViewModel: PollsViewModel
    private lateinit var pollsRecyclerView: RecyclerView
    private val localStorageService = LocalStorageService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_polls, container, false)
        val newPollButton: FloatingActionButton = view.findViewById(R.id.newPollButton)

        pollsViewModel =
            ViewModelProvider(this)[PollsViewModel::class.java]
        pollsRecyclerView = view.findViewById(R.id.pollsRecyclerView)

        val pollAdapter = PollAdapter(mutableListOf())
        pollAdapter.onItemClick = {
            openVoteDialog(it)
        }

        pollsRecyclerView.adapter = pollAdapter
        pollsRecyclerView.layoutManager = LinearLayoutManager(activity)
        pollsRecyclerView.setHasFixedSize(true)

        pollsViewModel.responseMessage.observe(viewLifecycleOwner, {
            Toast
                .makeText(activity, it, Toast.LENGTH_LONG)
                .show()
        })
        pollsViewModel.pollsList.observe(viewLifecycleOwner, {
            pollAdapter.updatePolls(it)
        })

        newPollButton.setOnClickListener { openNewPollDialog() }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pollsViewModel.findAllPollsByHouseCouncil()
    }

    private fun openNewPollDialog() {
        val houseCouncil = Gson().fromJson(
            localStorageService.getData("house-council-obj", null),
            HouseCouncil::class.java
        )
        val dialog = NewPollDialog(houseCouncil.residents.toList())

        dialog.setNewPollDialogListener(this)
        dialog.show(childFragmentManager, "New poll dialog")
    }

    private fun openVoteDialog(poll: Poll) {
        val dialog = VoteDialog(poll)

        dialog.setVoteDialogListener(this)
        dialog.show(childFragmentManager, "Vote dialog")
    }

    override fun saveUserInput(adminCandidateId: String) {
        pollsViewModel.chooseNewAdmin(adminCandidateId)
    }

    override fun saveVote(voteStatus: VoteStatus, pollId: String) {
        pollsViewModel.vote(voteStatus, pollId)
    }
}