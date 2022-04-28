package com.bunggae.rendezvous.user.adapter

import com.bunggae.rendezvous.user.adapter.repository.UserJpaRepository
import com.bunggae.rendezvous.user.application.aggregate.UserAggregate
import com.bunggae.rendezvous.user.domain.User
import com.bunggae.rendezvous.user.record.toJpaRecord
import org.springframework.stereotype.Component

@Component
class UserAggregateImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserAggregate {
    override fun save(user: User): User {
        userJpaRepository.save(user.toJpaRecord())
        return user
    }

    override fun findOneOrNull(id: Long): User? {
        val userRecord =
            userJpaRepository.findById(id) // Optional<UserJpaRecord> 를 반환

        if (userRecord.isEmpty) return null
        return userRecord.get().toEntity()
    }

    override fun findByEmailOrNull(email: String): User? {
        val userRecord =
            userJpaRepository.findByEmail(email)
        return userRecord?.toEntity()
    }
}