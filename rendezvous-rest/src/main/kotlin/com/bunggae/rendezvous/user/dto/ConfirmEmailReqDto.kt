package com.bunggae.rendezvous.user.dto

data class ConfirmEmailReqDto(
    val email: String,
    val code: String,
)
