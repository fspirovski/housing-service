package mk.ukim.finki.mpip.housing_service.ui.amenityItems

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mk.ukim.finki.mpip.housing_service.domain.model.AmenityItem
import mk.ukim.finki.mpip.housing_service.domain.model.AmenityItemStatus
import mk.ukim.finki.mpip.housing_service.domain.model.User
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
        CoroutineScope(Dispatchers.IO).launch {
            HousingService
                .findAllAmenityItemsByResidentAndStatus(
                    localStorageService.getData("current-user-id", "").toString(), AmenityItemStatus.PENDING
                )
                .enqueue(object : Callback<MutableList<AmenityItem>> {
                    override fun onResponse(
                        call: Call<MutableList<AmenityItem>>,
                        response: Response<MutableList<AmenityItem>>
                    ) {
                        if (response.isSuccessful) {
                            val amenityItems = response.body()!!

                            amenityItemsList.postValue(amenityItems)
                            responseError.postValue(false)
                        } else {
                            responseMessage.postValue("An error occurred! Error ${response.code()}.")
                            responseError.postValue(true)
                        }
                    }

                    override fun onFailure(call: Call<MutableList<AmenityItem>>, t: Throwable) {
                        responseMessage.postValue(t.message)
                        responseError.postValue(true)
                    }
                })
        }

    }
}