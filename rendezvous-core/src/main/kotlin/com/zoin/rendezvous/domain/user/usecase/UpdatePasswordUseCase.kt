package com.zoin.rendezvous.domain.user.usecase

import com.zoin.rendezvous.domain.user.User
import com.zoin.rendezvous.domain.user.repository.UserRepository
import com.zoin.rendezvous.util.PasswordEncoder
import javax.inject.Named
import javax.transaction.Transactional

@Named
class UpdatePasswordUseCase(
    private val passwordEncoder: PasswordEncoder,
) {
    data class Command(
        val user: User,
        val newPassword: String,
    )

    @Transactional
    fun execute(command: Command) {
        val (user, newPassword) = command
        val newSalt = passwordEncoder.generateSalt()
        user.changePassword(newSalt, passwordEncoder.encode(newSalt, newPassword))
        // 여기 더티체킹 보기
    }
}
