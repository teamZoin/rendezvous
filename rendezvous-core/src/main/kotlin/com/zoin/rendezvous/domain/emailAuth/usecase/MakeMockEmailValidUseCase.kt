package com.zoin.rendezvous.domain.emailAuth.usecase

import com.zoin.rendezvous.domain.emailAuth.repository.EmailAuthJpaRepository
import javax.inject.Named
import javax.transaction.Transactional

@Named
class MakeMockEmailValidUseCase(
    private val emailAuthJpaRepository: EmailAuthJpaRepository,
) {
    data class Command(
        val email: String,
    )

    @Transactional
    fun execute(command: Command) {
        val emailAuth = emailAuthJpaRepository.findByEmail(command.email) ?: throw IllegalArgumentException("이메일 인증 api를 먼저 실행해주세요")
        emailAuth.isVerified = true
    }
}
