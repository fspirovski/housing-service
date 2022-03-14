package mk.ukim.finki.mpip.housing_service.ui.pollDetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import mk.ukim.finki.mpip.housing_service.R

class PollDetailsFragment : Fragment() {

    private lateinit var pollDetailsViewModel: PollDetailsViewModel
    val args: PollDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pollDetailsViewModel = ViewModelProvider(this).get(PollDetailsViewModel::class.java)

        return inflater.inflate(R.layout.poll_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get single poll
        Log.d(Log.INFO.toString(), args.poll.toString())
    }

}