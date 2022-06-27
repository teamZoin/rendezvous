package com.zoin.rendezvous.domain.emailAuth.usecase

import com.zoin.rendezvous.domain.emailAuth.EmailAuth
import com.zoin.rendezvous.domain.emailAuth.repository.EmailAuthJpaRepository
import com.zoin.rendezvous.infra.MailService
import java.time.LocalDateTime
import javax.inject.Named
import javax.transaction.Transactional

@Named
class SendVerificationEmailUseCase(
    private val mailService: MailService,
    private val emailAuthJpaRepository: EmailAuthJpaRepository,
) {
    data class Command(
        val email: String
    )

    @Transactional
    fun execute(command: Command) {
        val (targetEmail) = command
        val randomCode = genVerificationCode()
        // TODO: 이미 있을 경우에 삭제해주기.
        emailAuthJpaRepository.save(
            EmailAuth(
                email = targetEmail,
                code = randomCode,
                expiresAt = LocalDateTime.now().plusMinutes(10)
            )
        )
        mailService.sendVerificationEmail(targetEmail, randomCode)
    }

    private fun genVerificationCode(): String {
        val length = 5
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}
