package mk.ukim.finki.mpip.housing_service.service.rest

import mk.ukim.finki.mpip.housing_service.domain.dto.*
import mk.ukim.finki.mpip.housing_service.domain.model.*
import retrofit2.Call
import retrofit2.http.*

interface HousingServiceApi {

    @POST("api/users/login")
    fun login(@Body loginDto: LoginDto): Call<AuthResponse>

    @POST("api/users/register")
    fun register(@Body registerDto: RegisterDto): Call<AuthResponse>

    @PUT("api/house-councils/{houseCouncilId}/join")
    fun joinHouseCouncil(@Path("houseCouncilId") houseCouncilId: String): Call<HouseCouncil>

    @POST("api/house-councils/register")
    fun registerHouseCouncil(@Body houseCouncilDto: HouseCouncil): Call<HouseCouncil>

    @GET("api/house-councils/{id}")
    fun findHouseCouncilById(@Path("id") id: String): Call<HouseCouncil>

    @GET("api/amenities/{houseCouncilId}/by-house-council")
    fun findAllAmenitiesByHouseCouncil(@Path("houseCouncilId") houseCouncilId: String): Call<MutableList<Amenity>>

    @GET("api/amenities/{houseCouncilId}/{status}/by-house-council-and-status")
    fun findAllAmenitiesByHouseCouncilAndStatus(
        @Path("houseCouncilId") houseCouncilId: String,
        @Path("status") status: String
    ): Call<MutableList<Amenity>>

    @GET("api/amenities/{id}")
    fun findAmenityById(@Path("id") id: String): Call<Amenity>

    @POST("api/amenities/create")
    fun createAmenity(@Body amenityDto: AmenityDto): Call<Amenity>

    @POST("api/amenities/choose-admin")
    fun chooseAdmin(@Body chooseAdminDto: ChooseAdminDto): Call<Poll>

    @PUT("api/amenities/{id}/edit")
    fun editAmenity(@Path("id") id: String, @Body amenityDto: AmenityDto): Call<Amenity>

    @DELETE("api/amenities/{id}/delete")
    fun deleteAmenity(@Path("id") id: String): Call<Amenity>

    @GET("api/amenity-items/{residentId}/{status}/by-resident-and-status")
    fun findAllAmenityItemsByResidentAndStatus(
        @Path("residentId") residentId: String,
        @Path("status") status: AmenityItemStatus?
    ): Call<MutableList<AmenityItem>>

    @GET("api/amenity-items/{id}")
    fun findAmenityItemById(@Path("id") id: String): Call<AmenityItem>

    @PUT("api/amenity-items/{id}/update")
    fun sendConfirmationOfPayment(
        @Path("id") id: String,
        @Body confirmationOfPaymentDto: ConfirmationOfPaymentDto
    ): Call<AmenityItem>

    @PUT("api/amenity-items/{id}/change-status")
    fun changeAmenityItemStatus(
        @Path("id") id: String,
        amenityItemStatusDto: AmenityItemStatusDto
    ): Call<AmenityItem>

    @GET("api/polls/{houseCouncilId}/by-house-council")
    fun findAllPollsByHouseCouncil(@Path("houseCouncilId") houseCouncilId: String): Call<MutableList<Poll>>

    @GET("api/polls/{id}")
    fun findPollById(@Path("id") id: String): Call<Poll>

    @GET("api/votes/{id}")
    fun findVoteById(@Path("id") id: String): Call<Vote>

    @POST("api/votes/{userId}/vote")
    fun vote(@Path("userId") userId: String, voteStatusDto: VoteStatusDto): Call<String>
}