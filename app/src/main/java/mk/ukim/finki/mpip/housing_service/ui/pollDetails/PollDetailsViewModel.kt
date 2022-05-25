package mk.ukim.finki.mpip.housing_service.ui.pollDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mk.ukim.finki.mpip.housing_service.domain.model.Poll
import mk.ukim.finki.mpip.housing_service.service.rest.HousingService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PollDetailsViewModel : ViewModel() {

    val poll = MutableLiveData<Poll>()
    val responseMessage = MutableLiveData<String>()
    val responseError = MutableLiveData<Boolean>()

    fun findPollById(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            HousingService.findPollById(id).enqueue(object :
                Callback<Poll> {
                override fun onResponse(
                    call: Call<Poll>,
                    response: Response<Poll>
                ) {
                    if (response.isSuccessful) {
                        val currentPoll = response.body()!!

                        poll.postValue(currentPoll)
                        responseError.postValue(false)
                    } else {
                        responseMessage.postValue("An error occurred! Error ${response.code()}.")
                        responseError.postValue(true)
                    }
                }

                override fun onFailure(call: Call<Poll>, t: Throwable) {
                    responseMessage.postValue(t.message)
                    responseError.postValue(true)
                }
            })
        }
    }
}