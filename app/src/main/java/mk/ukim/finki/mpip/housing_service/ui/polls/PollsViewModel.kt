package mk.ukim.finki.mpip.housing_service.ui.polls

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mk.ukim.finki.mpip.housing_service.domain.model.Poll
import mk.ukim.finki.mpip.housing_service.service.LocalStorageService
import mk.ukim.finki.mpip.housing_service.service.rest.HousingService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PollsViewModel : ViewModel() {

    private val localStorageService = LocalStorageService()
    val pollsList = MutableLiveData<MutableList<Poll>>()
    val responseMessage = MutableLiveData<String>()
    val responseError = MutableLiveData<Boolean>()

    fun findAllPollsByHouseCouncil() {
        HousingService
            .findAllPollsByHouseCouncil(
                localStorageService.getData("houseCouncilId", "").toString()
            )
            .enqueue(object : Callback<MutableList<Poll>> {
                override fun onResponse(
                    call: Call<MutableList<Poll>>,
                    response: Response<MutableList<Poll>>
                ) {
                    if (response.isSuccessful) {
                        val polls = response.body()!!

                        pollsList.value = polls
                        responseError.value = false
                    } else {
                        responseMessage.value = "Error ${response.code()}."
                        responseError.value = true
                    }
                }

                override fun onFailure(call: Call<MutableList<Poll>>, t: Throwable) {
                    responseMessage.value = t.message
                    responseError.value = true
                }
            })
    }
}