package com.bunggae.rendezvous.user.application.usecase

import com.bunggae.rendezvous.user.application.aggregate.UserAggregate
import com.bunggae.rendezvous.user.domain.User
import javax.inject.Named

@Named
class LoginUserUseCase(private val userAggregate: UserAggregate) {
    data class Query(val email: String, val password: String)

    fun execute(query: Query): User {
        return userAggregate.findByEmailAndPasswordOrNull(
            query.email,
            query.password
        ) ?: throw IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다.")
    }
}