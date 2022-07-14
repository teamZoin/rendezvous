package com.zoin.rendezvous.api.user.dto

import com.zoin.rendezvous.domain.user.UserVO

data class LoginResDto(
    val user: UserVO,
    val accessToken: String
)
