package com.zoin.rendezvous.api.report.dto

import com.zoin.rendezvous.domain.report.ReportReason

data class ReportRendezvousReqDto(
    val rendezvousId: Long,
    val reportReason: String,
    val etcDesc: String?,
)
