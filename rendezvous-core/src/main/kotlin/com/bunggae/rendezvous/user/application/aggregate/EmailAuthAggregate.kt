package com.bunggae.rendezvous.user.application.aggregate

import com.bunggae.rendezvous.user.domain.EmailAuth

interface EmailAuthAggregate {
    fun save(emailAuth: EmailAuth): EmailAuth
    fun findByEmailOrNull(email: String): EmailAuth?
}