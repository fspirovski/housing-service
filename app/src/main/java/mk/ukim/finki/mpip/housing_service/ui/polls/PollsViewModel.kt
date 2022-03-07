package mk.ukim.finki.mpip.housing_service.ui.polls

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        CoroutineScope(Dispatchers.IO).launch {
            HousingService
                .findAllPollsByHouseCouncil(
                    localStorageService.getData("house-council", "").toString()
                )
                .enqueue(object : Callback<MutableList<Poll>> {
                    override fun onResponse(
                        call: Call<MutableList<Poll>>,
                        response: Response<MutableList<Poll>>
                    ) {
                        if (response.isSuccessful) {
                            val polls = response.body()!!

                            pollsList.postValue(polls)
                            responseError.postValue(false)
                        } else {
                            responseMessage.postValue("An error occurred! Error ${response.code()}.")
                            responseError.postValue(true)
                        }
                    }

                    override fun onFailure(call: Call<MutableList<Poll>>, t: Throwable) {
                        responseMessage.postValue(t.message)
                        responseError.postValue(true)
                    }
                })
        }

    }
}