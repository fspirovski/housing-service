package mk.ukim.finki.mpip.housing_service.service.rest

import mk.ukim.finki.mpip.housing_service.domain.dto.AuthResponse
import mk.ukim.finki.mpip.housing_service.domain.dto.LoginDto
import mk.ukim.finki.mpip.housing_service.domain.dto.RegisterDto
import mk.ukim.finki.mpip.housing_service.domain.model.HouseCouncil
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HousingServiceApi {

    @POST("api/users/login")
    fun login(@Body loginDto: LoginDto): Call<AuthResponse>

    @POST("api/users/register")
    fun register(@Body registerDto: RegisterDto): Call<AuthResponse>

    @GET("api/house-councils/{id}")
    fun findHouseCouncilById(@Path("id") id: String): Call<HouseCouncil>
}