package mk.ukim.finki.mpip.housing_service.ui.polls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.mpip.housing_service.R

class PollsFragment : Fragment() {

    private lateinit var pollsViewModel: PollsViewModel
    private lateinit var pollsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_polls, container, false)

        pollsViewModel =
            ViewModelProvider(this)[PollsViewModel::class.java]
        pollsRecyclerView = view.findViewById(R.id.pollsRecyclerView)

        val pollAdapter = PollAdapter(mutableListOf())
        pollAdapter.onItemClick = {
            PollsFragmentDirections.actionPollsToPollDetailsFragment(it)
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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pollsViewModel.findAllPollsByHouseCouncil()
    }
}