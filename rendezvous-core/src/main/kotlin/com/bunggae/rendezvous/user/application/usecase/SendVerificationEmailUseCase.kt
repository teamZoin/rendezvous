package com.bunggae.rendezvous.user.application.usecase

import com.bunggae.rendezvous.user.application.infra.MailService
import javax.inject.Named

@Named
class SendVerificationEmailUseCase(
    private val mailService: MailService,
) {
    data class Command(
        val email: String
    )

    fun execute(command: Command) {
        val (targetEmail) = command
        val randomCode = genVerificationCode()
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