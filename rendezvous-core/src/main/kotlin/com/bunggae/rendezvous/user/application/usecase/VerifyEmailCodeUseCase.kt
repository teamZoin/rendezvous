package com.bunggae.rendezvous.user.application.usecase

import com.bunggae.rendezvous.user.application.aggregate.EmailAuthAggregate
import java.time.LocalDateTime
import javax.inject.Named

@Named
class VerifyEmailCodeUseCase(
    private val emailAuthAggregate: EmailAuthAggregate,
) {
    data class Query(
        val email: String,
        val code: String,
    )

    fun execute(input: Query): Boolean {
        val (email, code) = input
        val emailAuth = emailAuthAggregate.findByEmailOrNull(email)
            ?: throw IllegalArgumentException("Mail send to input email address not found")
        if (LocalDateTime.now() > emailAuth.expiresAt) {
            throw IllegalStateException("The code already expired.")
        }
        val isCorrect = code == emailAuth.code
        if (isCorrect) {
            emailAuth.isVerified = true
            emailAuthAggregate.save(emailAuth)
        }
        return isCorrect
    }
}