package com.zoin.rendezvous.domain.user.repository

import com.zoin.rendezvous.domain.user.User
import org.springframework.stereotype.Component

@Component
class UserCustomRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserCustomRepository {
    override fun findByIdExcludeDeleted(id: Long): User {
        val user = userJpaRepository.findById(id)
            .orElseThrow { IllegalArgumentException("User not found. id: $id") }
        if (user.deletedAt != null) throw IllegalStateException("The user(id: $id) was deleted.")
        return user
    }
}
