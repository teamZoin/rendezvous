package com.bunggae.rendezvous.user.record

import com.bunggae.rendezvous.user.domain.EmailAuth
import com.bunggae.rendezvous.base.JpaBaseEntity
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "email_auth")
class EmailAuthJpaRecord(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val email: String,
    val code: String,
    val isVerified: Boolean? = false,
) : JpaBaseEntity() {
    fun toEntity() = EmailAuth(
        email,
        code,
        createdAt.plusMinutes(10)
    )
}

fun EmailAuth.toRecord() = EmailAuthJpaRecord(
    email = email,
    code = code,
    isVerified = isVerified,
)