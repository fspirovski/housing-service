package mk.ukim.finki.mpip.housing_service.ui.auth.lobby

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        CoroutineScope(Dispatchers.IO).launch {
            HousingService
                .joinHouseCouncil(houseCouncilId)
                .enqueue(object : Callback<HouseCouncil> {
                    override fun onResponse(
                        call: Call<HouseCouncil>,
                        response: Response<HouseCouncil>
                    ) {
                        if (response.isSuccessful) {
                            val houseCouncil = response.body()!!

                            responseMessage.postValue("Welcome!")
                            responseError.postValue(false)
                            saveHouseCouncilInfo(houseCouncil)
                        } else {
                            responseMessage.postValue("An error occurred! Error ${response.code()}.")
                            responseError.postValue(true)
                        }
                    }

                    override fun onFailure(call: Call<HouseCouncil>, t: Throwable) {
                        responseMessage.postValue(t.message)
                        responseError.postValue(true)
                    }
                })
        }
    }

    fun registerHouseCouncil(addressOfResidence: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val houseCouncil = HouseCouncil(null, addressOfResidence, null, setOf())

            HousingService
                .registerHouseCouncil(houseCouncil)
                .enqueue(object : Callback<HouseCouncil> {
                    override fun onResponse(
                        call: Call<HouseCouncil>,
                        response: Response<HouseCouncil>
                    ) {
                        if (response.isSuccessful) {
                            val houseCouncil = response.body()!!

                            responseMessage.postValue("Welcome!")
                            responseError.postValue(false)
                            saveHouseCouncilInfo(houseCouncil)
                        } else {
                            responseMessage.postValue("An error occurred! Error ${response.code()}.")
                            responseError.postValue(true)
                        }
                    }

                    override fun onFailure(call: Call<HouseCouncil>, t: Throwable) {
                        responseMessage.postValue(t.message)
                        responseError.postValue(true)
                    }
                })
        }
    }

    private fun saveHouseCouncilInfo(houseCouncil: HouseCouncil) {
        localStorageService.saveData("house-council", houseCouncil.id)
    }
}