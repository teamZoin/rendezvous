package com.bunggae.rendezvous.user.domain

import com.bunggae.rendezvous.user.application.aggregate.UserAggregate
import java.time.LocalDateTime

class User(
    var id: Long? = null,
    var username: String,
    var email: String,
    var password: String,
    var profileImgUrl: String? = null,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    var deletedAt: LocalDateTime? = null,
) {
    fun isDeleted(): Boolean = deletedAt != null

    fun hasDuplicatedEmail(userAggregate: UserAggregate): Boolean {
        val alreadyUser = userAggregate.findByEmailOrNull(email)
        return alreadyUser != null
    }

    var agreedToPushNotification: Boolean = true
        private set

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (username != other.username) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (profileImgUrl != other.profileImgUrl) return false
        if (agreedToPushNotification != other.agreedToPushNotification) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + profileImgUrl.hashCode()
        result = 31 * result + agreedToPushNotification.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + updatedAt.hashCode()
        return result
    }

    override fun toString(): String {
        return "User(id=$id, username='$username', email='$email', password='$password', profileImgUrl='$profileImgUrl', agreedToPushNotification=$agreedToPushNotification, createdAt=$createdAt, updatedAt=$updatedAt)"
    }

}
