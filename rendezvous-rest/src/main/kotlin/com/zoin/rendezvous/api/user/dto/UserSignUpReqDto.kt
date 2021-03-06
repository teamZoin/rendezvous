package com.zoin.rendezvous.api.user.dto

data class UserSignUpReqDto(
    val email: String,
    val password: String,
    val userName: String,
    val serviceId: String,
    val profileImgUrl: String,
)
