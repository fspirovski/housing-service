package mk.ukim.finki.mpip.housing_service.ui.amenityItems

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mk.ukim.finki.mpip.housing_service.domain.model.AmenityItem
import mk.ukim.finki.mpip.housing_service.domain.model.AmenityItemStatus
import mk.ukim.finki.mpip.housing_service.service.LocalStorageService
import mk.ukim.finki.mpip.housing_service.service.rest.HousingService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AmenityItemsViewModel : ViewModel() {

    private val localStorageService = LocalStorageService()
    val amenityItemsList = MutableLiveData<MutableList<AmenityItem>>()
    val responseMessage = MutableLiveData<String>()
    val responseError = MutableLiveData<Boolean>()

    fun findAllAmenityItemsByResidentAndStatus(status: AmenityItemStatus?) {
        HousingService
            .findAllAmenityItemsByResidentAndStatus(localStorageService.getData("residentId", "").toString(), status)
            .enqueue(object : Callback<MutableList<AmenityItem>> {
                override fun onResponse(
                    call: Call<MutableList<AmenityItem>>,
                    response: Response<MutableList<AmenityItem>>
                ) {
                    if(response.isSuccessful) {
                        val amenityItems = response.body()!!

                        amenityItemsList.value = amenityItems
                        responseError.value = false
                    } else {
                        responseMessage.value = "Error ${response.code()}."
                        responseError.value = true
                    }
                }

                override fun onFailure(call: Call<MutableList<AmenityItem>>, t: Throwable) {
                    responseMessage.value = t.message
                    responseError.value = true
                }
            })
    }
}