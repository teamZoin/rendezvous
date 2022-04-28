package com.bunggae.rendezvous.user.adapter.repository

import com.bunggae.rendezvous.user.record.UserJpaRecord
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository: JpaRepository<UserJpaRecord, Long> {
    fun findByEmail(email: String): UserJpaRecord?
}