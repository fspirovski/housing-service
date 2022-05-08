package mk.ukim.finki.mpip.housing_service.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import mk.ukim.finki.mpip.housing_service.AuthActivity
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.domain.model.HouseCouncil
import mk.ukim.finki.mpip.housing_service.domain.model.User
import mk.ukim.finki.mpip.housing_service.service.LocalStorageService


class ProfileFragment : Fragment() {

    private val localStorageService = LocalStorageService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val user = Gson().fromJson(
            localStorageService.getData("current-user", null),
            User::class.java
        )

        val userNameSurname: TextView = view.findViewById(R.id.userNameSurname)
        val userPhone: TextView = view.findViewById(R.id.userPhoneNumber)
        val userEmail: TextView = view.findViewById(R.id.userEmail)
        val logoutButton: Button = view.findViewById(R.id.logoutButton)


        userNameSurname.text = "${user.name} ${user.surname}"
        userPhone.text = user.phoneNumber
        userEmail.text = user.emailAddress

        logoutButton.setOnClickListener {
            localStorageService.clearData()
            val intent = Intent(activity, AuthActivity::class.java)

            startActivity(intent)
            activity!!.finish()
        }

        return view
    }


}