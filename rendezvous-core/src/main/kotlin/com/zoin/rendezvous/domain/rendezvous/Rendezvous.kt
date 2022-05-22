package com.zoin.rendezvous.domain.rendezvous

import com.zoin.rendezvous.base.JpaBaseEntity
import com.zoin.rendezvous.base.SoftDeletable
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.domain.user.User
import org.hibernate.annotations.SQLDelete
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "rendezvous")
@SQLDelete(
    sql = """
    UPDATE rendezvous SET deleted_at = current_timestamp WHERE id = ?
"""
)
class Rendezvous(
    id: Long? = null,

    @ManyToOne
    val creator: User,

    title: String,
    appointmentTime: LocalDateTime,
    location: String,
    requiredParticipantsCount: Int,
    participants: List<User> = arrayListOf(),
    description: String? = null,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now(),
    override var deletedAt: LocalDateTime? = null,
) : JpaBaseEntity(createdAt, updatedAt), SoftDeletable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = id

    var title: String = title
        private set

    var description: String? = description
        private set

    var appointmentTime: LocalDateTime = appointmentTime
        private set

    var location: String = location
        private set

    var requiredParticipantsCount: Int = requiredParticipantsCount
        private set

    var isClosedByCreator: Boolean = false
        private set

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "belongsToRendezvous")
    var participants: List<User> = participants
        private set

    fun beDeletedBy(rendezvousRepository: RendezvousRepository, creator: User) {
        if (creator != this.creator) throw IllegalAccessException("번개 생성자가 아닙니다.")
        if (isClosedByCreator) throw IllegalStateException("이미 삭제된 번개입니다.")
        rendezvousRepository.delete(this)
    }

    fun isClosed(): Boolean {
        return isClosedByCreator || participants.size >= requiredParticipantsCount
    }

    fun addNewParticipant(user: User) {
        val newParticipantList: List<User> = listOf(*participants.toTypedArray(), user)

        participants = newParticipantList
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rendezvous

        if (creator != other.creator) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = creator.hashCode()
        result = 31 * result + (id?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Rendezvous(author=$creator, deletedAt=$deletedAt, id=$id, title='$title', datetime=$appointmentTime, place='$location', discription=$description)"
    }
}
