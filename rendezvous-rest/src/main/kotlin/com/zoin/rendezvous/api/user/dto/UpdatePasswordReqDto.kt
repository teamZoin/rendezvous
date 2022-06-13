package com.zoin.rendezvous.api.user.dto

data class UpdatePasswordReqDto(
    val password: String,
    val newPassword: String,
)
