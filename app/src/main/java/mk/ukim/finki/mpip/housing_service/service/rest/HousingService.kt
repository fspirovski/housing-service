package mk.ukim.finki.mpip.housing_service.service.rest

import mk.ukim.finki.mpip.housing_service.domain.dto.*
import mk.ukim.finki.mpip.housing_service.domain.model.*
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

    fun joinHouseCouncil(houseCouncilId: String): Call<HouseCouncil> =
        housingServiceApi.joinHouseCouncil(houseCouncilId)

    fun findAllAmenitiesByHouseCouncil(houseCouncilId: String): Call<MutableList<Amenity>> =
        housingServiceApi.findAllAmenitiesByHouseCouncil(houseCouncilId)

    fun findAllAmenitiesByHouseCouncilAndStatus(
        houseCouncilId: String,
        status: String
    ): Call<MutableList<Amenity>> =
        housingServiceApi.findAllAmenitiesByHouseCouncilAndStatus(houseCouncilId, status)

    fun findAmenityById(id: String): Call<Amenity> = housingServiceApi.findAmenityById(id)

    fun createAmenity(amenityDto: AmenityDto): Call<Amenity> =
        housingServiceApi.createAmenity(amenityDto)

    fun chooseAdmin(chooseAdminDto: ChooseAdminDto): Call<Poll> =
        housingServiceApi.chooseAdmin(chooseAdminDto)

    fun editAmenity(id: String, amenityDto: AmenityDto): Call<Amenity> =
        housingServiceApi.editAmenity(id, amenityDto)

    fun deleteAmenity(id: String): Call<Amenity> = housingServiceApi.deleteAmenity(id)

    fun findAllAmenityItemsByResidentAndStatus(
        residentId: String,
        status: AmenityItemStatus?
    ): Call<MutableList<AmenityItem>> =
        housingServiceApi.findAllAmenityItemsByResidentAndStatus(residentId, status)

    fun findAmenityItemById(id: String): Call<AmenityItem> =
        housingServiceApi.findAmenityItemById(id)

    fun changeAmenityItemStatus(
        id: String,
        amenityItemStatusDto: AmenityItemStatusDto
    ): Call<AmenityItem> = housingServiceApi.changeAmenityItemStatus(id, amenityItemStatusDto)

    fun findAllPollsByHouseCouncil(houseCouncilId: String): Call<MutableList<Poll>> =
        housingServiceApi.findAllPollsByHouseCouncil(houseCouncilId)

    fun findPollById(id: String): Call<Poll> = housingServiceApi.findPollById(id)

    fun findVoteById(id: String): Call<Vote> = housingServiceApi.findVoteById(id)

    fun vote(userId: String, voteStatusDto: VoteStatusDto): Call<String> =
        housingServiceApi.vote(userId, voteStatusDto)
}