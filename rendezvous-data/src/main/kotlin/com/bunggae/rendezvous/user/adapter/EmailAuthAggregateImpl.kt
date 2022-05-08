package com.bunggae.rendezvous.user.adapter

import com.bunggae.rendezvous.user.adapter.repository.EmailAuthJpaRepository
import com.bunggae.rendezvous.user.application.aggregate.EmailAuthAggregate
import com.bunggae.rendezvous.user.domain.EmailAuth
import com.bunggae.rendezvous.user.record.toRecord
import org.springframework.stereotype.Component

@Component
class EmailAuthAggregateImpl(
    private val emailAuthJpaRepository: EmailAuthJpaRepository,
) : EmailAuthAggregate {
    override fun save(emailAuth: EmailAuth): EmailAuth {
        val emailAuthRecord = emailAuthJpaRepository.save(emailAuth.toRecord())
        return emailAuthRecord.toEntity()
    }

    override fun findByEmailOrNull(email: String): EmailAuth? {
        TODO("Not yet implemented")
    }
}