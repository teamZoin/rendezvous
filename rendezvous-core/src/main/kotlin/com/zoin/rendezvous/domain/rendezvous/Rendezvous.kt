package com.zoin.rendezvous.domain.rendezvous

import com.zoin.rendezvous.base.JpaBaseEntity
import com.zoin.rendezvous.base.SoftDeletable
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousParticipantRepository
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.domain.user.User
import com.zoin.rendezvous.domain.user.UserVO
import org.hibernate.annotations.SQLDelete
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
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

    var currentParticipantsCount: Int = 1
        private set

    var isClosedByCreator: Boolean = false
        private set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rendezvous")
    var participants: MutableList<RendezvousParticipant> = arrayListOf()
        private set

    fun beDeletedBy(rendezvousRepository: RendezvousRepository, creator: User) {
        if (creator != this.creator) throw IllegalAccessException("번개 작성자가 아닙니다.")
        if (deletedAt != null) throw IllegalStateException("이미 삭제된 번개입니다.")
        rendezvousRepository.delete(this)
    }

    fun beClosedBy(rendezvousRepository: RendezvousRepository, user: User) {
        if (user != this.creator) throw IllegalAccessException("번개 작성자가 아닙니다.")
        if (isClosed()) throw IllegalStateException("이미 마감된 번개입니다.")
        isClosedByCreator = true
        rendezvousRepository.save(this)
    }

    fun isClosed(): Boolean {
        return isClosedByCreator || participants.size >= requiredParticipantsCount
    }

    fun addNewParticipant(user: User, rendezvousParticipantRepository: RendezvousParticipantRepository) {
        if (currentParticipantsCount >= requiredParticipantsCount) throw IllegalStateException("이미 마감된 번개입니다.")

        val rendezvousParticipant = RendezvousParticipant(
            rendezvous = this,
            user = user,
        )
        rendezvousParticipantRepository.save(rendezvousParticipant)

        currentParticipantsCount += 1
    }

    fun deleteNewParticipant(user: User, rendezvousParticipantRepository: RendezvousParticipantRepository) {
        val participant = rendezvousParticipantRepository.findByRendezvousAndParticipant(this, user)
            ?: throw IllegalStateException("이미 참여하지 않는 번개입니다.")
        rendezvousParticipantRepository.delete(participant)
        currentParticipantsCount -= 1
    }

    fun updateByCreator(
        title: String,
        appointmentTime: LocalDateTime,
        location: String,
        requiredParticipantsCount: Int,
        description: String? = null,
    ) {
        this.title = title
        this.appointmentTime = appointmentTime
        this.location = location
        this.requiredParticipantsCount = requiredParticipantsCount
        if (!description.isNullOrBlank()) this.description = description
    }

    fun mustGetId() = id ?: throw IllegalStateException("Entity doesn't have ID.")

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

data class RendezvousVO(
    val id: Long,
    val creator: UserVO,
    val title: String,
    val location: String,
    val appointmentTime: LocalDateTime,
    val requiredParticipantsCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val participants: List<UserVO>? = null,
    val description: String? = null,
    var isMyRendezvous: Boolean? = null,
) {
    companion object {
        fun of(rendezvous: Rendezvous, includeParticipants: Boolean = false) = RendezvousVO(
            id = rendezvous.mustGetId(),
            creator = UserVO.of(rendezvous.creator),
            title = rendezvous.title,
            location = rendezvous.location,
            appointmentTime = rendezvous.appointmentTime,
            requiredParticipantsCount = rendezvous.requiredParticipantsCount,
            createdAt = rendezvous.createdAt,
            updatedAt = rendezvous.updatedAt,
            description = rendezvous.description,
            participants = if (includeParticipants) rendezvous.participants.map { UserVO.of(it.participant) } else null
        )
    }

    fun addCreatorIdentity(userId: Long) {
        isMyRendezvous = userId == creator.id
    }
}
