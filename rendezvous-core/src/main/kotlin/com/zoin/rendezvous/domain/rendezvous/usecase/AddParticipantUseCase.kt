package com.zoin.rendezvous.domain.rendezvous.usecase

import com.zoin.rendezvous.domain.rendezvous.RendezvousParticipant
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousParticipantRepository
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.domain.user.repository.UserRepository
import javax.inject.Named

@Named
class AddParticipantUseCase(
    private val userRepository: UserRepository,
    private val rendezvousRepository: RendezvousRepository,
    private val rendezvousParticipantRepository: RendezvousParticipantRepository,
) {
    data class Command(
        val rendezvousId: Long,
        val userId: Long,
    )

    fun execute(command: Command) {
        val rendezvous = rendezvousRepository.findByIdExcludeDeleted(command.rendezvousId)
        val user = userRepository.findByIdExcludeDeleted(command.userId)
        val participants = rendezvous.participants.map { it.participant }

        if (participants.size >= rendezvous.requiredParticipantsCount) throw IllegalStateException("Participant exceeds.")
        if (participants.contains(user)) throw IllegalStateException("Already participated user.")

        rendezvousParticipantRepository.save(
            RendezvousParticipant(
                rendezvous = rendezvous,
                user = user,
            )
        )
    }
}
