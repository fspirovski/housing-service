package mk.ukim.finki.mpip.housing_service.domain.model

import java.io.Serializable

data class AmenityItem(
    val id: String,
    val amenity: Amenity,
    val resident: User,
    val status: AmenityItemStatus,
    val confirmationOfPayment: String
) : Serializable
