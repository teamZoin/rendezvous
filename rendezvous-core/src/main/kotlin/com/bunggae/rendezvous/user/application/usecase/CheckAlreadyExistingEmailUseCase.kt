package com.bunggae.rendezvous.user.application.usecase

import com.bunggae.rendezvous.user.application.aggregate.UserAggregate
import javax.inject.Named

@Named
class CheckAlreadyExistingEmailUseCase(
    private val userAggregate: UserAggregate,
) {
    data class Query(
        val email: String,
    )

    fun execute(query: Query): Boolean {
        userAggregate.findByEmailOrNull(query.email) ?: return false
        return true
    }
}