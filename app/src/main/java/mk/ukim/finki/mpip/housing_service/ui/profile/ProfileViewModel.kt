package mk.ukim.finki.mpip.housing_service.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mk.ukim.finki.mpip.housing_service.domain.dto.InviteMemberDto
import mk.ukim.finki.mpip.housing_service.service.LocalStorageService
import mk.ukim.finki.mpip.housing_service.service.rest.HousingService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    private val localStorageService = LocalStorageService()
    val responseMessage = MutableLiveData<String>()

    fun invite(email: String) {
        val inviteMemberDto =
            InviteMemberDto(email, localStorageService.getData("house-council", "").toString())

        CoroutineScope(Dispatchers.IO).launch {
            HousingService
                .invite(inviteMemberDto)
                .enqueue(object : Callback<Unit> {
                    override fun onResponse(
                        call: Call<Unit>,
                        response: Response<Unit>
                    ) {
                        if (response.isSuccessful) {
                            responseMessage.postValue("Email successfully sent!")
                        } else {
                            responseMessage.postValue("An error occurred! Error ${response.code()}.")
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        responseMessage.postValue(t.message)
                    }
                })
        }
    }
}