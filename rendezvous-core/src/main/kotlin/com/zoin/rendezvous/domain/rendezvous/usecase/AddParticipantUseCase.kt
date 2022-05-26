package com.zoin.rendezvous.domain.rendezvous.usecase

import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousParticipantRepository
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.domain.user.repository.UserRepository
import org.springframework.data.jpa.repository.Lock
import javax.inject.Named
import javax.persistence.LockModeType
import javax.transaction.Transactional

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

    @Transactional
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    fun execute(command: Command) {
        val rendezvous = rendezvousRepository.findByIdExcludeDeleted(command.rendezvousId)
        val user = userRepository.findByIdExcludeDeleted(command.userId)

        rendezvous.increaseParticipantsCount()

        val participants = rendezvous.participants.map { it.participant }
        if (participants.contains(user)) throw IllegalStateException("Already participated user.")

        rendezvous.addNewParticipant(user, rendezvousParticipantRepository)
    }
}
