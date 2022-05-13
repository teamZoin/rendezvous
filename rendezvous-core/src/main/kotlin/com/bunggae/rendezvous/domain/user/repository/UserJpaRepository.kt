package com.bunggae.rendezvous.domain.user.repository

import com.bunggae.rendezvous.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByServiceId(serviceId: String): User?
}
