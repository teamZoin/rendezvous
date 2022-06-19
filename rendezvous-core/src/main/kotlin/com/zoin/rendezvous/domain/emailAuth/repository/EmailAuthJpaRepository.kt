package com.zoin.rendezvous.domain.emailAuth.repository

import com.zoin.rendezvous.domain.emailAuth.EmailAuth
import org.springframework.data.jpa.repository.JpaRepository

interface EmailAuthJpaRepository: JpaRepository<EmailAuth, Long> {
    fun findByEmail(email:String): EmailAuth?
}
