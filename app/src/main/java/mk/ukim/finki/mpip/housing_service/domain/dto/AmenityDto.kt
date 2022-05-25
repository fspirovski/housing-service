package mk.ukim.finki.mpip.housing_service.domain.dto

data class AmenityDto(
    val title: String,
    val description: String,
    val amount: Double,
    val houseCouncilId: String
)
