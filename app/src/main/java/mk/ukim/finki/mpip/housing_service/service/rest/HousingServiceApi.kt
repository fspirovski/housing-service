package mk.ukim.finki.mpip.housing_service.service.rest

import mk.ukim.finki.mpip.housing_service.domain.dto.AuthResponse
import mk.ukim.finki.mpip.housing_service.domain.dto.LoginDto
import mk.ukim.finki.mpip.housing_service.domain.dto.RegisterDto
import mk.ukim.finki.mpip.housing_service.domain.model.HouseCouncil
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface HousingServiceApi {

    @POST("api/users/login")
    fun login(@Body loginDto: LoginDto): Call<AuthResponse>

    @POST("api/users/register")
    fun register(@Body registerDto: RegisterDto): Call<AuthResponse>

    @PUT("api/house-councils/{houseCouncilId}/join")
    fun joinHouseCouncil(@Path("houseCouncilId") houseCouncilId: String): Call<HouseCouncil>
}