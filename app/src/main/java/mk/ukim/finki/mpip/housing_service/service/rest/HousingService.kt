package mk.ukim.finki.mpip.housing_service.service.rest

import mk.ukim.finki.mpip.housing_service.domain.dto.AuthResponse
import mk.ukim.finki.mpip.housing_service.domain.dto.LoginDto
import mk.ukim.finki.mpip.housing_service.domain.dto.RegisterDto
import mk.ukim.finki.mpip.housing_service.domain.model.HouseCouncil
import mk.ukim.finki.mpip.housing_service.service.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HousingService {

    private const val baseUrl = "http://10.0.2.2:8080/"
    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()
    private val housingServiceApi = Retrofit.Builder()
        .client(client)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(HousingServiceApi::class.java)

    fun login(loginDto: LoginDto): Call<AuthResponse> = housingServiceApi.login(loginDto)

    fun register(registerDto: RegisterDto): Call<AuthResponse> =
        housingServiceApi.register(registerDto)

    fun findHouseCouncilById(id: String): Call<HouseCouncil> =
        housingServiceApi.findHouseCouncilById(id)
}