package com.zoin.rendezvous.api.user.dto

class UpdateUserProfileImageReqDto(
    val profileImgUrl: String
)

class UpdateUserProfileImageResDto(
    val userId: Long,
    val profileImgUrl: String
)
