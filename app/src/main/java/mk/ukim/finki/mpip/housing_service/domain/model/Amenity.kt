package mk.ukim.finki.mpip.housing_service.domain.model

import java.time.LocalDateTime

data class Amenity(
    val id: String,
    val title: String,
    val description: String,
    val amount: String,
    val createdAt: LocalDateTime,
    val status: AmenityStatus,
    val houseCouncil: HouseCouncil
)