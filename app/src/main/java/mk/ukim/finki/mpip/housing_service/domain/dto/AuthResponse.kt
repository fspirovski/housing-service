package mk.ukim.finki.mpip.housing_service.domain.dto

import mk.ukim.finki.mpip.housing_service.domain.model.User

data class AuthResponse(
    val message: String,
    val jwt: String?,
    val currentUser: User?
)
