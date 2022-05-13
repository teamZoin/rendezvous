package com.bunggae.rendezvous.domain.user.usecase

import com.bunggae.rendezvous.domain.user.repository.UserRepository
import javax.inject.Named

@Named
class CheckAlreadyExistingEmailUseCase(
    private val userRepository: UserRepository,
) {
    data class Query(
        val email: String,
    )

    fun execute(query: Query): Boolean =
        userRepository.findByEmail(query.email) != null
}
