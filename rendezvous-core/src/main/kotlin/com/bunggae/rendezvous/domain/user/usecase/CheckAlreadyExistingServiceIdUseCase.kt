package com.bunggae.rendezvous.domain.user.usecase

import com.bunggae.rendezvous.domain.user.repository.UserRepository
import javax.inject.Named

@Named
class CheckAlreadyExistingServiceIdUseCase(
    private val userRepository: UserRepository,
) {
    data class Query(
        val serviceId: String
    )

    fun execute(query: Query): Boolean =
        userRepository.findByServiceId(query.serviceId) != null
}
