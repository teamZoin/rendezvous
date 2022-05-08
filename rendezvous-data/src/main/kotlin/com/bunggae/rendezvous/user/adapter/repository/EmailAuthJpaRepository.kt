package com.bunggae.rendezvous.user.adapter.repository

import com.bunggae.rendezvous.user.record.EmailAuthJpaRecord
import org.springframework.data.jpa.repository.JpaRepository

interface EmailAuthJpaRepository: JpaRepository<EmailAuthJpaRecord, Long> {
}