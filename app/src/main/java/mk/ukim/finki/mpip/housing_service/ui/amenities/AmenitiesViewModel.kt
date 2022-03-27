package mk.ukim.finki.mpip.housing_service.ui.amenities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mk.ukim.finki.mpip.housing_service.domain.dto.AmenityDto
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

    fun findAllAmenitiesByHouseCouncil() {
        CoroutineScope(Dispatchers.IO).launch {
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

                            amenitiesList.postValue(amenities)
                        } else {
                            responseMessage.postValue("An error occurred! Error ${response.code()}.")
                        }
                    }

                    override fun onFailure(call: Call<MutableList<Amenity>>, t: Throwable) {
                        responseMessage.postValue(t.message)
                    }
                })
        }
    }

    fun createAmenity(title: String, description: String, amount: Double) {
        val houseCouncilId = localStorageService.getData("house-council", "")!!
        val amenityDto = AmenityDto(title, description, amount, houseCouncilId)

        CoroutineScope(Dispatchers.IO).launch {
            HousingService
                .createAmenity(amenityDto)
                .enqueue(object : Callback<Amenity> {
                    override fun onResponse(
                        call: Call<Amenity>,
                        response: Response<Amenity>
                    ) {
                        if (response.isSuccessful) {
                            findAllAmenitiesByHouseCouncil()
                        } else {
                            responseMessage.postValue("An error occurred! Error ${response.code()}.")
                        }
                    }

                    override fun onFailure(call: Call<Amenity>, t: Throwable) {
                        responseMessage.postValue(t.message)
                    }
                })
        }
    }
}