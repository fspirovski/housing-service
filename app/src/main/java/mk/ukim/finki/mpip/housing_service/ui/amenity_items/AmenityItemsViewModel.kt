package mk.ukim.finki.mpip.housing_service.ui.amenity_items

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mk.ukim.finki.mpip.housing_service.domain.dto.ConfirmationOfPaymentDto
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

    fun findAllAmenityItemsByResidentAndStatus(amenityItemStatus: AmenityItemStatus) {
        CoroutineScope(Dispatchers.IO).launch {
            HousingService
                .findAllAmenityItemsByResidentAndStatus(
                    localStorageService.getData("current-user-id", "").toString(),
                    amenityItemStatus
                )
                .enqueue(object : Callback<MutableList<AmenityItem>> {
                    override fun onResponse(
                        call: Call<MutableList<AmenityItem>>,
                        response: Response<MutableList<AmenityItem>>
                    ) {
                        if (response.isSuccessful) {
                            val amenityItems = response.body()!!

                            amenityItemsList.postValue(amenityItems)
                        } else {
                            responseMessage.postValue("An error occurred! Error ${response.code()}.")
                        }
                    }

                    override fun onFailure(call: Call<MutableList<AmenityItem>>, t: Throwable) {
                        responseMessage.postValue(t.message)
                    }
                })
        }
    }

    fun sendConfirmationOfPayment(
        id: String,
        confirmationOfPaymentDto: ConfirmationOfPaymentDto
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            HousingService
                .sendConfirmationOfPayment(id, confirmationOfPaymentDto)
                .enqueue(object : Callback<AmenityItem> {
                    override fun onResponse(
                        call: Call<AmenityItem>,
                        response: Response<AmenityItem>
                    ) {
                        if (response.isSuccessful) {
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