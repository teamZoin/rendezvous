package com.bunggae.rendezvous.user.domain

import com.bunggae.rendezvous.user.application.aggregate.UserAggregate
import javax.inject.Named

@Named
class UserValidator(
    private val userAggregate: UserAggregate,
) {
    fun validate(newUser: User) {
        if (hasDuplicatedEmail(newUser.email)) throw IllegalArgumentException("email already exists.")
        if (hasDuplicatedServiceId(newUser.serviceId)) throw IllegalArgumentException("service id already exists.")
    }

    private fun hasDuplicatedEmail(email: String): Boolean {
        val alreadyUser = userAggregate.findByEmailOrNull(email)
        return alreadyUser != null
    }

    private fun hasDuplicatedServiceId(serviceId: String): Boolean {
        val alreadyUser = userAggregate.findByServiceIdOrNull(serviceId)
        return alreadyUser != null
    }
}