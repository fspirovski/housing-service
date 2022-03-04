package mk.ukim.finki.mpip.housing_service.ui.polls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.ui.polls.adapter.PollAdapter

class PollsFragment : Fragment() {

    private lateinit var pollsViewModel: PollsViewModel
    private lateinit var pollsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pollsViewModel =
            ViewModelProvider(this).get(PollsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_polls, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pollsRecyclerView = view.findViewById(R.id.pollsRecyclerView)
        pollsRecyclerView.layoutManager = LinearLayoutManager(activity)
        pollsRecyclerView.setHasFixedSize(true)
        pollsRecyclerView.adapter = PollAdapter(mutableListOf())

        pollsViewModel.findAllPollsByHouseCouncil()
    }
}