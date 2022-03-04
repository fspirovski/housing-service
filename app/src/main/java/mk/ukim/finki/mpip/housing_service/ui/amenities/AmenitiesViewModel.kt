package mk.ukim.finki.mpip.housing_service.ui.amenities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mk.ukim.finki.mpip.housing_service.domain.model.Amenity
import mk.ukim.finki.mpip.housing_service.service.LocalStorageService
import mk.ukim.finki.mpip.housing_service.service.rest.HousingService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AmenitiesViewModel : ViewModel() {

    private val localStorageService = LocalStorageService()
    val amenitiesList = MutableLiveData<MutableList<Amenity>>()
    val responseMessage = MutableLiveData<String>()
    val responseError = MutableLiveData<Boolean>()

    fun findAllAmenitiesByHouseCouncil() {
        HousingService
            .findAllAmenitiesByHouseCouncil(
                localStorageService.getData("house-council", "").toString()
            )
            .enqueue(object : Callback<MutableList<Amenity>> {
                override fun onResponse(
                    call: Call<MutableList<Amenity>>,
                    response: Response<MutableList<Amenity>>
                ) {
                    if (response.isSuccessful) {
                        val amenities = response.body()!!

                        amenitiesList.value = amenities
                        responseError.value = false
                    } else {
                        responseMessage.value = "Error ${response.code()}."
                        responseError.value = true
                    }
                }

                override fun onFailure(call: Call<MutableList<Amenity>>, t: Throwable) {
                    responseMessage.value = t.message
                    responseError.value = true
                }
            })
    }
}