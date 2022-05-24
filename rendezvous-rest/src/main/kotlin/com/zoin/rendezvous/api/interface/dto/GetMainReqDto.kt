package com.zoin.rendezvous.api.`interface`.dto

data class GetMainReqDto(
    val size: Long,
    val cursorId: Long? = null,
)
