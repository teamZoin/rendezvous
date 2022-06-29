package com.zoin.rendezvous.domain.emailAuth.usecase

import com.zoin.rendezvous.domain.emailAuth.repository.EmailAuthJpaRepository
import java.time.LocalDateTime
import javax.inject.Named
import javax.transaction.Transactional

@Named
class VerifyEmailByCodeUseCase(
    private val emailAuthJpaRepository: EmailAuthJpaRepository,
) {
    data class Query(
        val email: String,
        val code: String,
    )

    @Transactional
    fun execute(query: Query) {
        val emailAuth =
            emailAuthJpaRepository.findByEmail(query.email) ?: throw IllegalArgumentException("인증 메일을 전송하지 않은 이메일입니다.")
        val currentTime = LocalDateTime.now()
        if (emailAuth.expiresAt.isBefore(currentTime)) {
            throw IllegalStateException("인증 시간이 만료되었습니다.")
        }
        if (query.code != emailAuth.code) {
            throw IllegalArgumentException("인증 번호가 올바르지 않습니다.")
        }
        emailAuth.isVerified = true
    }
}
