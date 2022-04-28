package com.bunggae.rendezvous.user.application.usecase

import com.bunggae.rendezvous.user.application.aggregate.UserAggregate
import com.bunggae.rendezvous.user.domain.User
import javax.inject.Named

@Named
class CreateUserUseCase(
    private val userAggregate: UserAggregate,
) {
    data class Command(
        val username: String,
        val password: String,
        val email: String,
    )
    fun execute(command: Command) {
        val (username, password, email) = command
        val user = User(
            username = username,
            password = password,
            email = email,
        )
        if (user.hasDuplicatedEmail(userAggregate)) throw IllegalArgumentException("Duplicated email.")
        userAggregate.save(user)
    }
}