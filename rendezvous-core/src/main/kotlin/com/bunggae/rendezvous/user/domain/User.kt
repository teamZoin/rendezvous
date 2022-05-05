package com.bunggae.rendezvous.user.domain

import java.time.LocalDateTime

class User(
    var id: Long? = null,
    var email: String,
    var hashedPassword: ByteArray,
    var salt: ByteArray,
    var serviceId: String,
    var userName: String,
    var profileImgUrl: String? = null,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    var deletedAt: LocalDateTime? = null,
) {
    var agreedToPushNotification: Boolean = true

    fun isDeleted(): Boolean = deletedAt != null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + email.hashCode()
        return result
    }
}
