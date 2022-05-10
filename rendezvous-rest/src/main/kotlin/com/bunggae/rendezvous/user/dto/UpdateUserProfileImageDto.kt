package com.bunggae.rendezvous.user.dto

class UpdateUserProfileImageReqDto(
    val userId: Long, // TODO: header JWT 로 대체
    val profileImgUrl: String
)

class UpdateUserProfileImageResDto(
    val userId: Long,
    val profileImgUrl: String
)
