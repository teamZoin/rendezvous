package com.zoin.rendezvous.domain.rendezvous

import com.zoin.rendezvous.base.JpaBaseEntity
import com.zoin.rendezvous.domain.user.User
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "user_participated_in_rendezvous")
class RendezvousParticipant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    rendezvous: Rendezvous,
    user: User,
) : JpaBaseEntity() {
    @ManyToOne
    @JoinColumn(name = "rendezvous_id")
    var rendezvous: Rendezvous = rendezvous
        private set

    @ManyToOne
    @JoinColumn(name = "user_id")
    var participant: User = user
        private set

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RendezvousParticipant

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
