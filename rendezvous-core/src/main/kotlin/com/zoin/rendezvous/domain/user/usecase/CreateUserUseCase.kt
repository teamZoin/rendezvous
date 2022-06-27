package com.zoin.rendezvous.domain.user.usecase

import com.zoin.rendezvous.domain.user.User
import com.zoin.rendezvous.domain.user.UserValidator
import com.zoin.rendezvous.domain.user.repository.UserRepository
import com.zoin.rendezvous.util.PasswordEncoder
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

    fun execute(command: Command): User {
        val (email, password, userName, serviceId, profileImgUrl) = command
        // TODO: 검증된 이메일인지 체크하기
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
        return userRepository.save(user)
    }
}
