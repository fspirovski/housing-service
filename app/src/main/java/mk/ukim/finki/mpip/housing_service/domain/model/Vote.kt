package mk.ukim.finki.mpip.housing_service.domain.model

data class Vote(
    val id: String,
    val status: VoteStatus?,
    val voter: User?
)
