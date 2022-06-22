package com.zoin.rendezvous.api.report.dto

data class ReportRendezvousReqDto(
    val rendezvousId: Long,
    val reportReason: String,
    val etcDesc: String?,
)
