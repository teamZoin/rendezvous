package com.zoin.rendezvous.domain.user

import com.zoin.rendezvous.base.JpaBaseEntity
import com.zoin.rendezvous.base.SoftDeletable
import org.hibernate.annotations.SQLDelete
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user")
@SQLDelete(
    sql = """
    UPDATE user SET deleted_at = current_timestamp WHERE id = ?
"""
)
class User(
    id: Long? = null,
    email: String,
    hashedPassword: ByteArray,
    salt: ByteArray,
    serviceId: String,
    userName: String,
    profileImgUrl: String? = null,
    agreedToPushNotifications: Boolean = true,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now(),
    override var deletedAt: LocalDateTime? = null,
) : JpaBaseEntity(createdAt, updatedAt), SoftDeletable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    var agreedToPushNotifications: Boolean = agreedToPushNotifications
        private set

    fun isDeleted(): Boolean = deletedAt != null

    fun changeProfileImg(newUrl: String) {
        profileImgUrl = newUrl
    }

    fun agreeToGetNotification() {
        agreedToPushNotifications = true
    }

    fun disagreeToGetNotification() {
        agreedToPushNotifications = false
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