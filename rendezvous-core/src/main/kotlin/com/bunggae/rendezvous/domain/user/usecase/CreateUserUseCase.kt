package com.bunggae.rendezvous.domain.user.usecase

import com.bunggae.rendezvous.domain.user.User
import com.bunggae.rendezvous.domain.user.UserValidator
import com.bunggae.rendezvous.domain.user.repository.UserRepository
import com.bunggae.rendezvous.util.PasswordEncoder
import javax.inject.Named

@Named
class CreateUserUseCase(
    private val userRepository: UserRepository,
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
        userRepository.save(user)
    }
}
