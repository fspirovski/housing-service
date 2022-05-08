package mk.ukim.finki.mpip.housing_service.ui.polls

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import mk.ukim.finki.mpip.housing_service.R
import java.util.HashMap
import android.widget.AdapterView

import mk.ukim.finki.mpip.housing_service.domain.model.User

import android.view.View


class NewPollDialog(val residents: List<User>) : AppCompatDialogFragment() {
    interface NewPollDialogListener {
        fun saveUserInput(adminCandidateId: String)
    }

    private lateinit var newPollDialogListener: NewPollDialog.NewPollDialogListener

    fun setNewPollDialogListener(newPollDialogListener: NewPollDialog.NewPollDialogListener) {
        this.newPollDialogListener = newPollDialogListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity?.layoutInflater?.inflate(R.layout.new_poll_dialog, null)

        val adminCandidateId: Spinner? = view?.findViewById(R.id.newAdminCandidateDropdown)

//        val residentsArray = residents.map { resident -> resident.toS }

        val spinnerAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, residents)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adminCandidateId!!.adapter = spinnerAdapter

        adminCandidateId.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val user = parent?.getItemAtPosition(position) as User
                Toast.makeText(context, user.id, Toast.LENGTH_LONG).show()
            }
        }

        return builder.setView(view)
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Save") { _, _ ->
                val selectedUser = adminCandidateId?.selectedItem as User

                val validationResult = validateUserInput(selectedUser.id)

                if (validationResult.containsKey(true)) {
                    newPollDialogListener.saveUserInput(selectedUser.id)
                } else {
                    Toast
                        .makeText(activity, validationResult[false], Toast.LENGTH_LONG)
                        .show()
                }
            }
            .create()
    }

    private fun validateUserInput(
        adminCandidateId: String,
    ): Map<Boolean, String> {
        if (adminCandidateId.isEmpty() || adminCandidateId.isBlank()) {
            return mapOf(false to "The 'AdminCandidate' field is required.")
        }

        return mapOf(true to "Valid poll input.")
    }
}