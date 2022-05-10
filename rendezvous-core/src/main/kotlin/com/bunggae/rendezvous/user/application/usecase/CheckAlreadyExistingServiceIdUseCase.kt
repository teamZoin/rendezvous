package com.bunggae.rendezvous.user.application.usecase

import com.bunggae.rendezvous.user.application.aggregate.UserAggregate
import javax.inject.Named

@Named
class CheckAlreadyExistingServiceIdUseCase(
    private val userAggregate: UserAggregate,
) {
    data class Query(
        val serviceId: String
    )

    fun execute(query: Query): Boolean =
        userAggregate.findByServiceIdOrNull(query.serviceId) != null
}
