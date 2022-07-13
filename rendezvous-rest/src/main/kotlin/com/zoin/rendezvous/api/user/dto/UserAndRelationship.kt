package com.zoin.rendezvous.api.user.dto

import com.zoin.rendezvous.domain.user.UserVO

data class UserAndRelationship(
    val user: UserVO,
    val relationshipOrder: Int,
)
