package com.zoin.rendezvous.domain.user

import com.zoin.rendezvous.base.JpaBaseEntity
import com.zoin.rendezvous.base.SoftDeletable
import com.zoin.rendezvous.domain.rendezvous.Rendezvous
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import org.hibernate.annotations.SQLDelete
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
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

    // TODO: 필드를 도메인 모델에서 제거. (분리하기)
    var agreedToPushNotifications: Boolean = agreedToPushNotifications
        private set

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    var madeRendezvous: List<Rendezvous> = listOf()

    fun mustGetId() = id ?: throw IllegalStateException("Entity doesn't have ID.")

    fun deleteRendezvous(rendezvousRepository: RendezvousRepository, rendezvous: Rendezvous) {
        val mutableRendezvousList = madeRendezvous.toMutableList()
        mutableRendezvousList -= rendezvous
        madeRendezvous = mutableRendezvousList.toList()

        rendezvousRepository.delete(rendezvous)
    }

    fun isDeleted(): Boolean = deletedAt != null

    fun changeProfileImg(newUrl: String) {
        profileImgUrl = newUrl
    }

    fun changeUsername(newName: String) {
        userName = newName
    }

    fun changePassword(newSalt: ByteArray, newHashedPassword: ByteArray) {
        this.salt = newSalt
        this.hashedPassword = newHashedPassword
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

    override fun toString(): String {
        return "User(id=$id, email='$email', serviceId='$serviceId', userName='$userName')"
    }
}

data class UserVO(
    val id: Long,
    val serviceId: String,
    val userName: String,
    val email: String,
    val profileImgUrl: String? = null,
    var agreedToPushNotifications: Boolean,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
) {
    companion object {
        fun of(user: User) = UserVO(
            id = user.mustGetId(),
            serviceId = user.serviceId,
            userName = user.userName,
            email = user.email,
            profileImgUrl = user.profileImgUrl,
            agreedToPushNotifications = user.agreedToPushNotifications,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt,
        )
    }
}
