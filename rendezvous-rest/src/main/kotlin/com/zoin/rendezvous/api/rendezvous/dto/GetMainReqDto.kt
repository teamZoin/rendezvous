package com.zoin.rendezvous.api.rendezvous.dto

data class GetMainReqDto(
    val size: Long,
    val cursorId: Long? = null,
)
