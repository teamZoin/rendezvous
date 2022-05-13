package com.bunggae.rendezvous.domain.user

import com.bunggae.rendezvous.domain.user.repository.UserRepository
import javax.inject.Named

@Named
class UserValidator(
    private val userRepository: UserRepository,
) {
    fun validate(newUser: User) {
        if (hasDuplicatedEmail(newUser.email)) throw IllegalArgumentException("email already exists.")
        if (hasDuplicatedServiceId(newUser.serviceId)) throw IllegalArgumentException("service id already exists.")
    }

    private fun hasDuplicatedEmail(email: String): Boolean {
        val alreadyUser = userRepository.findByEmail(email)
        return alreadyUser != null
    }

    private fun hasDuplicatedServiceId(serviceId: String): Boolean {
        val alreadyUser = userRepository.findByServiceId(serviceId)
        return alreadyUser != null
    }
}
