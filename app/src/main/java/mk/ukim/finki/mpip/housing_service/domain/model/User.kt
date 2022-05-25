package mk.ukim.finki.mpip.housing_service.domain.model

data class User(
    val id: String,
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val apartmentNumbers: List<Long>,
    val emailAddress: String,
    val password: String
) {
    override fun toString(): String {
        return "$name $surname"
    }
}
