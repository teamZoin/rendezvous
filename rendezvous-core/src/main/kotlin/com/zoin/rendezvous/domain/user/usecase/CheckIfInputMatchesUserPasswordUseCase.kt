package com.zoin.rendezvous.domain.user.usecase

import com.zoin.rendezvous.domain.user.repository.UserRepository
import com.zoin.rendezvous.util.PasswordEncoder
import javax.inject.Named

@Named
class CheckIfInputMatchesUserPasswordUseCase(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    data class Query(
        val userId: Long,
        val input: String,
    )

    fun execute(query: Query): Boolean {
        val user = userRepository.findByIdExcludeDeleted(query.userId)
        val encodedInput = passwordEncoder.encode(user.salt, query.input)
        return user.hashedPassword.contentEquals(encodedInput)
    }
}
