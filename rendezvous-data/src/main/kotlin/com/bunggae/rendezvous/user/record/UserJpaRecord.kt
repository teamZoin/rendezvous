package com.bunggae.rendezvous.user.record

import com.bunggae.rendezvous.base.JpaSoftDeletableEntity
import com.bunggae.rendezvous.user.domain.User
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
class UserJpaRecord(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var email: String,
    var password: String,
    var serviceId: String,
    var userName: String,
    var profileImgUrl: String? = null,
    override var deletedAt: LocalDateTime? = null,
) : JpaSoftDeletableEntity(deletedAt) {

    fun toEntity(): User = User(
        id = id,
        email = email,
        password = password,
        serviceId = serviceId,
        userName = userName,
        profileImgUrl = profileImgUrl,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
    )
}

fun User.toJpaRecord(): UserJpaRecord {
    val record = UserJpaRecord(
        id = id,
        email = email,
        password = password,
        serviceId = serviceId,
        userName = userName,
        profileImgUrl = profileImgUrl
    )
    record.createdAt = createdAt
    record.updatedAt = updatedAt
    record.deletedAt = deletedAt
    return record
}