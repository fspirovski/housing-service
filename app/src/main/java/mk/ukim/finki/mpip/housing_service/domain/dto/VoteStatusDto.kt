package mk.ukim.finki.mpip.housing_service.domain.dto

import mk.ukim.finki.mpip.housing_service.domain.model.VoteStatus

data class VoteStatusDto(
    val voteStatus: VoteStatus,
    val pollId: String
)
