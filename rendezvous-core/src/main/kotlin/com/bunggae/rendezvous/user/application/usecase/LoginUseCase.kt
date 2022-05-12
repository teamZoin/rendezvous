package com.bunggae.rendezvous.user.application.usecase

import com.bunggae.rendezvous.user.application.aggregate.UserAggregate
import com.bunggae.rendezvous.util.PasswordEncoder
import javax.inject.Named

@Named
class LoginUseCase(
    private val userAggregate: UserAggregate,
    private val passwordEncoder: PasswordEncoder,
) {
    data class Query(val email: String, val password: String)

    fun execute(query: Query): Long {
        val user = userAggregate.findByEmailOrNull(query.email) ?: throw IllegalArgumentException("존재하지 않는 이메일입니다.")
        val hashedPassword = passwordEncoder.encode(user.salt, query.password)
        if (!user.hashedPassword.contentEquals(hashedPassword)) throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        return user.id!!
    }
}
