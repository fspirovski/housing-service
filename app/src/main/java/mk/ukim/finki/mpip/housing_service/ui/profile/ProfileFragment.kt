package mk.ukim.finki.mpip.housing_service.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import mk.ukim.finki.mpip.housing_service.AuthActivity
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.User
import mk.ukim.finki.mpip.housing_service.service.LocalStorageService
import mk.ukim.finki.mpip.housing_service.ui.amenity_items.AmenityItemsViewModel

class ProfileFragment : Fragment() {

    private val localStorageService = LocalStorageService()
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var amenityItemsViewModel: AmenityItemsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        profileViewModel =
            ViewModelProvider(this)[ProfileViewModel::class.java]

        amenityItemsViewModel =
            ViewModelProvider(this)[AmenityItemsViewModel::class.java]

        val user = Gson().fromJson(
            localStorageService.getData("current-user", null),
            User::class.java
        )

        val userNameSurname: TextView = view.findViewById(R.id.userNameSurname)
        val userPhone: TextView = view.findViewById(R.id.userPhoneNumber)
        val userEmail: TextView = view.findViewById(R.id.userEmail)
        val logoutButton: Button = view.findViewById(R.id.logoutButton)

        val adminView: View = view.findViewById(R.id.adminView)
        val newUserEmail: EditText = view.findViewById(R.id.newUserEmail)
        val inviteUserButton: Button = view.findViewById(R.id.inviteUserButton)

        if (!amenityItemsViewModel.isAdmin()) {
            adminView.isVisible = false
            newUserEmail.isVisible = false
            inviteUserButton.isVisible = false
        }

        profileViewModel.responseMessage.observe(viewLifecycleOwner, {
            newUserEmail.text.clear()

            Toast
                .makeText(activity, it, Toast.LENGTH_LONG)
                .show()
        })

        userNameSurname.text = "${user.name} ${user.surname}"
        userPhone.text = user.phoneNumber
        userEmail.text = user.emailAddress

        logoutButton.setOnClickListener {
            localStorageService.clearData()
            val intent = Intent(activity, AuthActivity::class.java)

            startActivity(intent)
            activity!!.finish()
        }

        inviteUserButton.setOnClickListener {
            if (!Patterns.EMAIL_ADDRESS.matcher(newUserEmail.text.toString()).matches()) {
                Toast.makeText(activity, "Invalid email address format.", Toast.LENGTH_LONG).show()
            }

            profileViewModel.invite(newUserEmail.text.toString())
        }

        return view
    }
}