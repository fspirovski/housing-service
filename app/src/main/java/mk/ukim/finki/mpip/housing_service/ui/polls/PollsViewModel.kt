package mk.ukim.finki.mpip.housing_service.ui.polls

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mk.ukim.finki.mpip.housing_service.domain.dto.ChooseAdminDto
import mk.ukim.finki.mpip.housing_service.domain.dto.VoteStatusDto
import mk.ukim.finki.mpip.housing_service.domain.model.Poll
import mk.ukim.finki.mpip.housing_service.domain.model.VoteStatus
import mk.ukim.finki.mpip.housing_service.service.LocalStorageService
import mk.ukim.finki.mpip.housing_service.service.rest.HousingService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PollsViewModel : ViewModel() {

    private val localStorageService = LocalStorageService()
    val pollsList = MutableLiveData<MutableList<Poll>>()
    val responseMessage = MutableLiveData<String>()

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
                        } else {
                            responseMessage.postValue("An error occurred! Error ${response.code()}.")
                        }
                    }

                    override fun onFailure(call: Call<MutableList<Poll>>, t: Throwable) {
                        responseMessage.postValue(t.message)
                    }
                })
        }

    }

    fun chooseNewAdmin(adminCandidateId: String) {
        val houseCouncilId = localStorageService.getData("house-council", "")!!
        val chooseAdminDto = ChooseAdminDto(
            adminCandidateId,
            houseCouncilId
        )

        CoroutineScope(Dispatchers.IO).launch {
            HousingService.chooseAdmin(chooseAdminDto).enqueue(object : Callback<Poll> {
                override fun onResponse(call: Call<Poll>, response: Response<Poll>) {
                    if (response.isSuccessful) {
                        findAllPollsByHouseCouncil()
                    } else {
                        responseMessage.postValue("Your vote has already been submitted.")
                    }
                }

                override fun onFailure(call: Call<Poll>, t: Throwable) {
                    responseMessage.postValue(t.message)
                }
            })
        }
    }

    fun vote(voteStatus: VoteStatus, pollId: String) {
        val currentUserId = localStorageService.getData("current-user-id", "").toString()
        val voteStatusDto = VoteStatusDto(voteStatus, pollId)

        CoroutineScope(Dispatchers.IO).launch {
            HousingService.vote(currentUserId, voteStatusDto).enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        findAllPollsByHouseCouncil()
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