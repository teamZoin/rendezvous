package com.bunggae.rendezvous.user.application.usecase

import com.bunggae.rendezvous.user.application.aggregate.UserAggregate
import com.bunggae.rendezvous.user.domain.User
import com.bunggae.rendezvous.user.domain.UserValidator
import com.bunggae.rendezvous.util.PasswordEncoder
import javax.inject.Named

@Named
class CreateUserUseCase(
    private val userAggregate: UserAggregate,
    private val userValidator: UserValidator,
    private val passwordEncoder: PasswordEncoder,
) {
    data class Command(
        val email: String,
        val password: String,
        val userName: String,
        val serviceId: String,
        val profileImgUrl: String,
    )
    fun execute(command: Command) {
        val (email, password, userName, serviceId, profileImgUrl) = command
        val salt = passwordEncoder.generateSalt()
        val user = User(
            userName = userName,
            hashedPassword = passwordEncoder.encode(salt, password),
            salt = salt,
            email = email,
            serviceId = serviceId,
            profileImgUrl = profileImgUrl,
        )

        userValidator.validate(user)
        userAggregate.save(user)
    }
}
