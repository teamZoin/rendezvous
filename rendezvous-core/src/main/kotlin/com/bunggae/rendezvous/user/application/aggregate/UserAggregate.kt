package com.bunggae.rendezvous.user.application.aggregate

import com.bunggae.rendezvous.user.domain.User

interface UserAggregate {
    // create
    fun save(user: User): User

    // read
    fun findOneOrNull(id: Long): User?
    fun findByEmailOrNull(email: String): User?
    fun findByServiceIdOrNull(serviceId: String): User?
}