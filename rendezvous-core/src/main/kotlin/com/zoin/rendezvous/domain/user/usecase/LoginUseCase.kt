package com.zoin.rendezvous.domain.user.usecase

import com.zoin.rendezvous.domain.user.User
import com.zoin.rendezvous.domain.user.repository.UserRepository
import com.zoin.rendezvous.util.PasswordEncoder
import javax.inject.Named

@Named
class LoginUseCase(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    data class Query(val email: String, val password: String)

    fun execute(query: Query): User {
        val user = userRepository.findByEmail(query.email) ?: throw IllegalArgumentException("존재하지 않는 이메일입니다.")
        val hashedPassword = passwordEncoder.encode(user.salt, query.password)
        if (!user.hashedPassword.contentEquals(hashedPassword)) throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        return user
    }
}
