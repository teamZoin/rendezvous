package com.bunggae.rendezvous.infra

interface MailService {
    fun sendVerificationEmail(targetEmail: String, code: String)
}
