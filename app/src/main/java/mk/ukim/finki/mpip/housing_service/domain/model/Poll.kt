package mk.ukim.finki.mpip.housing_service.domain.model

import java.io.Serializable

data class Poll(
    val id: String,
    val adminCandidate: User,
    val amenityCandidate: Amenity,
    val votes: List<Vote>
): Serializable
