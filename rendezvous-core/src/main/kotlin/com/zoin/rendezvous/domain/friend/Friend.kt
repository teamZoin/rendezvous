package com.zoin.rendezvous.domain.friend

import com.zoin.rendezvous.base.JpaBaseEntity
import com.zoin.rendezvous.domain.user.User
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "friend")
class Friend(
    @ManyToOne
    val user: User,
    @ManyToOne
    val friend: User,
    status: Status,
) : JpaBaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Enumerated(value = EnumType.STRING)
    var status: Status = status
        private set

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Friend

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Friend(user=$user, friend=$friend, id=$id)"
    }
}

enum class Status {
    FRIEND,
    PENDING
}
