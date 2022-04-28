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
    UPDATE user SET updated_at = current_timestamp WHERE id = ?
"""
)
class UserJpaRecord(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var username: String,
    var email: String,
    var password: String,
    var profileImgUrl: String? = null,
    override var deletedAt: LocalDateTime? = null,
) : JpaSoftDeletableEntity(deletedAt) {
    fun toEntity(): User = User(id, username, email, password, profileImgUrl, createdAt, updatedAt, deletedAt)
}

fun User.toJpaRecord(): UserJpaRecord {
    val record = UserJpaRecord(
        id, username, email, password, profileImgUrl
    )
    record.createdAt = createdAt
    record.updatedAt = updatedAt
    record.deletedAt = deletedAt
    return record
}