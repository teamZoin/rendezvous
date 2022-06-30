package com.zoin.rendezvous.infra

import com.zoin.rendezvous.domain.user.UserQuitLog

interface MailService {
    fun sendVerificationEmail(targetEmail: String, code: String)
    fun sendQuitLog(userQuitLog: UserQuitLog)
}
