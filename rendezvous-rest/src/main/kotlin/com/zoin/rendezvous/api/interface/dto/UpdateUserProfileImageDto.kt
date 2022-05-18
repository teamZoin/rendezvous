package com.zoin.rendezvous.api.`interface`.dto

class UpdateUserProfileImageReqDto(
    val profileImgUrl: String
)

class UpdateUserProfileImageResDto(
    val userId: Long,
    val profileImgUrl: String
)
