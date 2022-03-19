package mk.ukim.finki.mpip.housing_service.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mk.ukim.finki.mpip.housing_service.domain.dto.AuthResponse
import mk.ukim.finki.mpip.housing_service.domain.dto.LoginDto
import mk.ukim.finki.mpip.housing_service.domain.dto.RegisterDto
import mk.ukim.finki.mpip.housing_service.service.LocalStorageService
import mk.ukim.finki.mpip.housing_service.service.rest.HousingService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel : ViewModel() {

    private val localStorageService = LocalStorageService()
    val authMessage = MutableLiveData<String>()
    val authError = MutableLiveData<Boolean>()

    fun login(emailAddress: String, password: String) {
        val loginDto = LoginDto(emailAddress, password)

        CoroutineScope(Dispatchers.IO).launch {
            HousingService
                .login(loginDto)
                .enqueue(object : Callback<AuthResponse> {
                    override fun onResponse(
                        call: Call<AuthResponse>,
                        response: Response<AuthResponse>
                    ) {
                        if (response.isSuccessful) {
                            val authResponse = response.body()!!

                            authMessage.postValue(authResponse.message)
                            authError.postValue(false)
                            saveAuthInfo(authResponse)
                        } else {
                            val gson = Gson()
                            val authResponse = gson.fromJson(
                                response.errorBody()?.charStream(),
                                AuthResponse::class.java
                            )

                            authMessage.postValue(authResponse.message)
                            authError.postValue(true)
                        }
                    }

                    override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                        authMessage.postValue(t.message)
                        authError.postValue(true)
                    }
                })
        }
    }

    fun register(
        name: String,
        surname: String,
        phoneNumber: String,
        emailAddress: String,
        password: String
    ) {
        val registerDto = RegisterDto(name, surname, phoneNumber, emailAddress, password)

        CoroutineScope(Dispatchers.IO).launch {
            HousingService
                .register(registerDto)
                .enqueue(object : Callback<AuthResponse> {
                    override fun onResponse(
                        call: Call<AuthResponse>,
                        response: Response<AuthResponse>
                    ) {
                        if (response.isSuccessful) {
                            val authResponse = response.body()!!

                            authMessage.postValue(authResponse.message)
                            authError.postValue(false)
                            saveAuthInfo(authResponse)
                        } else {
                            val gson = Gson()
                            val authResponse = gson.fromJson(
                                response.errorBody()?.charStream(),
                                AuthResponse::class.java
                            )

                            authMessage.postValue(authResponse.message)
                            authError.postValue(true)
                        }
                    }

                    override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                        authMessage.postValue(t.message)
                        authError.postValue(true)
                    }
                })
        }
    }

    private fun saveAuthInfo(authResponse: AuthResponse) {
        localStorageService.saveData("current-user", authResponse.currentUser!!.toString())
        localStorageService.saveData("current-user-id", authResponse.currentUser.id)
        localStorageService.saveData("jwt", authResponse.jwt!!)
    }
}