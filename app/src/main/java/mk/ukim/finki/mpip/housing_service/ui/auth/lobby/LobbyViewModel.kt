package mk.ukim.finki.mpip.housing_service.ui.auth.lobby

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mk.ukim.finki.mpip.housing_service.domain.model.HouseCouncil
import mk.ukim.finki.mpip.housing_service.service.LocalStorageService
import mk.ukim.finki.mpip.housing_service.service.rest.HousingService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LobbyViewModel : ViewModel() {

    private val localStorageService = LocalStorageService()
    val responseMessage = MutableLiveData<String>()
    val responseError = MutableLiveData<Boolean>()

    fun joinHouseCouncil(houseCouncilId: String) {
        HousingService
            .joinHouseCouncil(houseCouncilId)
            .enqueue(object : Callback<HouseCouncil> {
                override fun onResponse(
                    call: Call<HouseCouncil>,
                    response: Response<HouseCouncil>
                ) {
                    if (response.isSuccessful) {
                        val houseCouncil = response.body()!!

                        responseMessage.value = "Welcome!"
                        responseError.value = false
                        saveHouseCouncilInfo(houseCouncil)
                    } else {
//                        val gson = Gson()
//
//                        responseMessage.value = gson.fromJson(
//                            response.errorBody()?.charStream(),
//                            String::class.java
//                        )
                        responseMessage.value = "An error occurred! Error ${response.code()}."
                        responseError.value = true
                    }
                }

                override fun onFailure(call: Call<HouseCouncil>, t: Throwable) {
                    responseMessage.value = t.message
                    responseError.value = true
                }
            })
    }

    private fun saveHouseCouncilInfo(houseCouncil: HouseCouncil) {
        localStorageService.saveData("house-council", houseCouncil.toString())
    }
}