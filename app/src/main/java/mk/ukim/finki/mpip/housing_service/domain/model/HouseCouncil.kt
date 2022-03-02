package mk.ukim.finki.mpip.housing_service.domain.model

data class HouseCouncil(
    val id: String,
    val addressOfResidence: String,
    val admin: User,
    val residents: List<User>
)