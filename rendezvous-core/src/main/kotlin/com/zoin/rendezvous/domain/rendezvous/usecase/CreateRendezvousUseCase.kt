package com.zoin.rendezvous.domain.rendezvous.usecase

import com.zoin.rendezvous.domain.rendezvous.Rendezvous
import com.zoin.rendezvous.domain.rendezvous.RendezvousParticipant
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousParticipantRepository
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.domain.user.repository.UserRepository
import java.time.LocalDateTime
import javax.inject.Named
import javax.transaction.Transactional

@Named
class CreateRendezvousUseCase(
    private val userRepository: UserRepository,
    private val rendezvousRepository: RendezvousRepository,
    private val rendezvousParticipantRepository: RendezvousParticipantRepository,
) {
    data class Command(
        val creatorId: Long,
        val title: String,
        val appointmentTime: LocalDateTime,
        val location: String,
        val requiredParticipantsCount: Int,
        val description: String? = null,
    )

    @Transactional
    fun execute(command: Command) {
        val (creatorId, title, appointmentTime, location, requiredParticipantsCount, description) = command
        val creator =
            userRepository.findByIdExcludeDeleted(creatorId)
        val newRendezvous = rendezvousRepository.save(
            Rendezvous(
                creator = creator,
                title = title,
                appointmentTime = appointmentTime,
                location = location,
                requiredParticipantsCount = requiredParticipantsCount,
                description = description,
            )
        )
        rendezvousParticipantRepository.save(
            RendezvousParticipant(
                rendezvous = newRendezvous,
                user = creator,
            )
        )
    }
}
