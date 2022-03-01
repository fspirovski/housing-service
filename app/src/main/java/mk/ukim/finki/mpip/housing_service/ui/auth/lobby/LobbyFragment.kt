package mk.ukim.finki.mpip.housing_service.ui.auth.lobby

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mk.ukim.finki.mpip.housing_service.MainActivity
import mk.ukim.finki.mpip.housing_service.R

class LobbyFragment : Fragment() {

    private lateinit var lobbyViewModel: LobbyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lobby, container, false)

        lobbyViewModel = ViewModelProvider(this)[LobbyViewModel::class.java]

        val houseCouncilId: EditText = view.findViewById(R.id.houseCouncilId)
        val joinHouseCouncilButton: Button = view.findViewById(R.id.joinHouseCouncilButton)
        val registerHouseCouncilButton: Button = view.findViewById(R.id.registerHouseCouncilButton)

        joinHouseCouncilButton.setOnClickListener {
            val id = houseCouncilId.text.toString().trim()

            if (id.isEmpty() || id.isBlank()) {
                val message = "The 'id' field is required."

                Toast
                    .makeText(activity, message, Toast.LENGTH_LONG)
                    .show()
            } else {
                lobbyViewModel.findHouseCouncilById(id)
            }
        }

        lobbyViewModel.responseMessage.observe(this, {
            Toast
                .makeText(activity, it, Toast.LENGTH_LONG)
                .show()
        })

        lobbyViewModel.responseError.observe(this, {
            if (!it) {
                redirectToMainActivity()
            }
        })

        registerHouseCouncilButton.setOnClickListener {
            TODO("Navigate to a new activity.")
        }

        return view;
    }

    private fun redirectToMainActivity() {
        val intent = Intent(activity, MainActivity::class.java)

        startActivity(intent)
        activity?.finish()
    }
}