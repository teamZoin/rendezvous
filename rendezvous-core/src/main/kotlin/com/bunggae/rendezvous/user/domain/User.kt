package com.bunggae.rendezvous.user.domain

import java.time.LocalDateTime

class User(
    id: Long? = null,
    email: String,
    hashedPassword: ByteArray,
    salt: ByteArray,
    serviceId: String,
    userName: String,
    profileImgUrl: String? = null,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now(),
    deletedAt: LocalDateTime? = null,
    agreedToPushNotification: Boolean = true,
) {
    var id: Long? = id
    var email: String = email
        private set
    var hashedPassword: ByteArray = hashedPassword
        private set
    var salt: ByteArray = salt
        private set
    var serviceId: String = serviceId
        private set
    var userName: String = userName
        private set
    var profileImgUrl: String? = profileImgUrl
        private set
    var createdAt: LocalDateTime = createdAt
        private set
    var updatedAt: LocalDateTime = updatedAt
        private set
    var deletedAt: LocalDateTime? = deletedAt
        private set
    var agreedToPushNotification: Boolean = agreedToPushNotification
        private set

    fun isDeleted(): Boolean = deletedAt != null

    fun changeProfileImg(newUrl: String) {
        profileImgUrl = newUrl
    }

    fun agreeToGetNotification() {
        agreedToPushNotification = true
    }

    fun disagreeToGetNotification() {
        agreedToPushNotification = false
    }

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
