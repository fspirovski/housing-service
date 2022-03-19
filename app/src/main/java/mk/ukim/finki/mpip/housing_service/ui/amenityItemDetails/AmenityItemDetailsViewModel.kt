package mk.ukim.finki.mpip.housing_service.ui.amenityItemDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mk.ukim.finki.mpip.housing_service.domain.model.AmenityItem
import mk.ukim.finki.mpip.housing_service.service.rest.HousingService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AmenityItemDetailsViewModel : ViewModel() {
    val amenityItem = MutableLiveData<AmenityItem>()
    val responseMessage = MutableLiveData<String>()
    val responseError = MutableLiveData<Boolean>()

    fun findAmenityItemById(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            HousingService.findAmenityItemById(id).enqueue(object :
                Callback<AmenityItem> {
                override fun onResponse(
                    call: Call<AmenityItem>,
                    response: Response<AmenityItem>
                ) {
                    if (response.isSuccessful) {
                        val currentAmenityItem = response.body()!!

                        amenityItem.postValue(currentAmenityItem)
                        responseError.postValue(false)
                    } else {
                        responseMessage.postValue("An error occurred! Error ${response.code()}.")
                        responseError.postValue(true)
                    }
                }

                override fun onFailure(call: Call<AmenityItem>, t: Throwable) {
                    responseMessage.postValue(t.message)
                    responseError.postValue(true)
                }
            })
        }
    }
}