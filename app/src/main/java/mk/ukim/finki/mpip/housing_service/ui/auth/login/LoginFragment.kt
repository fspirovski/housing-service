package mk.ukim.finki.mpip.housing_service.ui.auth.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mk.ukim.finki.mpip.housing_service.R
import mk.ukim.finki.mpip.housing_service.ui.auth.AuthViewModel

class LoginFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        val emailAddress: EditText = view.findViewById(R.id.loginEmailAddress)
        val password: EditText = view.findViewById(R.id.loginPassword)
        val loginButton: Button = view.findViewById(R.id.loginButton)
        val registerButton: Button = view.findViewById(R.id.navigateToRegistrationButton)

        loginButton.setOnClickListener {
            login(
                emailAddress.text.toString().trim(),
                password.text.toString()
            )
        }

        authViewModel.authMessage.observe(viewLifecycleOwner, {
            Toast
                .makeText(activity, it, Toast.LENGTH_LONG)
                .show()
        })

        authViewModel.authError.observe(viewLifecycleOwner, {
            if (!it) {
                redirectToLobbyFragment()
            }
        })

        registerButton.setOnClickListener {
            redirectToRegisterFragment()
        }

        return view
    }

    private fun login(emailAddress: String, password: String) {
        val validationResult = validateLoginInput(emailAddress, password)

        if (validationResult.containsKey(true)) {
            authViewModel.login(emailAddress, password)
        } else {
            Toast
                .makeText(activity, validationResult[false], Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun validateLoginInput(emailAddress: String, password: String): Map<Boolean, String> {
        if (emailAddress.isEmpty() || emailAddress.isBlank()) {
            return mapOf(false to "The 'email address' field is required.")
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            return mapOf(false to "Invalid email address format.")
        }

        if (password.isEmpty() || password.isBlank()) {
            return mapOf(false to "The 'password' field is required.")
        }

        return mapOf(true to "Valid login input.")
    }

    private fun redirectToLobbyFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_lobbyFragment)
    }

    private fun redirectToRegisterFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }
}