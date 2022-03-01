package mk.ukim.finki.mpip.housing_service.domain.dto

data class RegisterDto(
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val emailAddress: String,
    val password: String,
)
