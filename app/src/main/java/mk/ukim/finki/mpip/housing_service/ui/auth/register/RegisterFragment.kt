package mk.ukim.finki.mpip.housing_service.ui.auth.register

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

class RegisterFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        val name: EditText = view.findViewById(R.id.registerName)
        val surname: EditText = view.findViewById(R.id.registerSurname)
        val phoneNumber: EditText = view.findViewById(R.id.registerPhoneNumber)
        val emailAddress: EditText = view.findViewById(R.id.registerEmailAddress)
        val password: EditText = view.findViewById(R.id.registerPassword)
        val confirmRegisterPassword: EditText = view.findViewById(R.id.confirmRegisterPassword)
        val registerButton: Button = view.findViewById(R.id.registerButton)
        val loginButton: Button = view.findViewById(R.id.navigateToLoginButton)

        registerButton.setOnClickListener {
            register(
                name.text.toString().trim(),
                surname.text.toString().trim(),
                phoneNumber.text.toString().trim(),
                emailAddress.text.toString().trim(),
                password.text.toString(),
                confirmRegisterPassword.text.toString()
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

        loginButton.setOnClickListener {
            redirectToLoginFragment()
        }

        return view
    }

    private fun register(
        name: String,
        surname: String,
        phoneNumber: String,
        emailAddress: String,
        password: String,
        confirmRegisterPassword: String
    ) {
        val validationResult =
            validateRegisterInput(
                name,
                surname,
                phoneNumber,
                emailAddress,
                password,
                confirmRegisterPassword
            )

        if (validationResult.containsKey(true)) {
            authViewModel.register(name, surname, phoneNumber, emailAddress, password)
        } else {
            Toast
                .makeText(activity, validationResult[false], Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun validateRegisterInput(
        name: String,
        surname: String,
        phoneNumber: String,
        emailAddress: String,
        password: String,
        confirmRegisterPassword: String
    ): Map<Boolean, String> {
        if (name.isEmpty() || name.isBlank()) {
            return mapOf(false to "The 'name' field is required.")
        }

        if (surname.isEmpty() || surname.isBlank()) {
            return mapOf(false to "The 'surname' field is required.")
        }

        if (phoneNumber.isEmpty() || phoneNumber.isBlank()) {
            return mapOf(false to "The 'phone number' field is required.")
        }

        if (!phoneNumber.matches(Regex("07[01245689]/[0-9]{3}-[0-9]{3}"))) {
            return mapOf(false to "Invalid phone number format.")
        }

        if (emailAddress.isEmpty() || emailAddress.isBlank()) {
            return mapOf(false to "The 'email address' field is required.")
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            return mapOf(false to "Invalid email address format.")
        }

        if (password.isEmpty() || password.isBlank()) {
            return mapOf(false to "The 'password' field is required.")
        }

        if (password != confirmRegisterPassword) {
            return mapOf(false to "The 'password' and 'confirm password' fields do not match.")
        }

        return mapOf(true to "Valid register input.")
    }

    private fun redirectToLobbyFragment() {
        findNavController().navigate(R.id.action_registerFragment_to_lobbyFragment)
    }

    private fun redirectToLoginFragment() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }
}