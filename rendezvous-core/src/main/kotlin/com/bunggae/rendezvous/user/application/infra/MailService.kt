package com.bunggae.rendezvous.user.application.infra

interface MailService {
    fun sendVerificationEmail(targetEmail: String, code: String)
}
