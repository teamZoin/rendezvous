package com.bunggae.rendezvous.user.application.usecase

import com.bunggae.rendezvous.user.application.aggregate.EmailAuthAggregate
import com.bunggae.rendezvous.user.application.infra.MailService
import com.bunggae.rendezvous.user.domain.EmailAuth
import javax.inject.Named

@Named
class SendVerificationEmailUseCase(
    private val mailService: MailService,
    private val emailAuthAggregate: EmailAuthAggregate,
) {
    data class Command(
        val email: String
    )

    fun execute(command: Command) {
        val (targetEmail) = command
        val randomCode = genVerificationCode()

        mailService.sendVerificationEmail(targetEmail, randomCode)

        val newEmailAuth = EmailAuth(
            email = targetEmail,
            code = randomCode,
        )
        emailAuthAggregate.save(newEmailAuth)
    }

    private fun genVerificationCode(): String {
        val length = 5
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}