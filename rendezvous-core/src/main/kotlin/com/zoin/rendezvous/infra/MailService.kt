package com.zoin.rendezvous.infra

interface MailService {
    fun sendVerificationEmail(targetEmail: String, code: String)
}
