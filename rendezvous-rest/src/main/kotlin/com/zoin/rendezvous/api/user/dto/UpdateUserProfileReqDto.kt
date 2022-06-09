package com.zoin.rendezvous.api.user.dto

data class UpdateUserProfileReqDto(
    val newUserName: String? = null,
    val newProfileImgUrl: String? = null,
)

data class UpdateUserProfileResDto(
    val updatedUserName: String,
    val updatedProfileImgUrl: String? = null,
)
