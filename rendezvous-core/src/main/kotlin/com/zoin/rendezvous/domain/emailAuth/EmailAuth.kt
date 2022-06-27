package com.zoin.rendezvous.domain.emailAuth

import com.zoin.rendezvous.base.JpaBaseEntity
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "email_auth")
class EmailAuth(
    val email: String,
    val code: String,
    val expiresAt: LocalDateTime,
): JpaBaseEntity() {

    var isVerified: Boolean = false

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
