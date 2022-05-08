package com.bunggae.rendezvous.user.domain

import java.time.LocalDateTime

data class EmailAuth(
    val email: String,
    val code: String,
    val expiresAt: LocalDateTime? = null,
)
