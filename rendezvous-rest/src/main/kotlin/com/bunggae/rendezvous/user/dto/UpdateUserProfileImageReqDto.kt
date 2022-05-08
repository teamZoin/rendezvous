package com.bunggae.rendezvous.user.dto

class UpdateUserProfileImageReqDto(
    val userId: Long,
    val profileImgUrl: String
)

class UpdateUserProfileImageResDto(
    val userId: Long,
    val profileImgUrl: String
)