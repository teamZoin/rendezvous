package com.zoin.rendezvous.api.rendezvous.dto

import java.time.LocalDateTime

data class SaveRendezvousReqDto(
    val title: String,
    val appointmentTime: LocalDateTime,
    val location: String,
    val requiredParticipantsCount: Int,
    val description: String? = null,
)
