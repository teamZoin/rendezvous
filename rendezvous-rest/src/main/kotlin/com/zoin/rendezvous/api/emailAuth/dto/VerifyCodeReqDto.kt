package com.zoin.rendezvous.api.emailAuth.dto

data class VerifyCodeReqDto(
    val email: String,
    val code: String,
)
