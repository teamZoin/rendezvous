package com.zoin.rendezvous.domain.rendezvous.usecase

import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.domain.user.repository.UserRepository
import java.time.LocalDateTime
import javax.inject.Named
import javax.transaction.Transactional

@Named
class UpdateRendezvousUseCase(
    private val rendezvousRepository: RendezvousRepository,
    private val userRepository: UserRepository,
) {
    data class Command(
        val userId: Long,
        val rendezvousId: Long,
        val title: String,
        val appointmentTime: LocalDateTime,
        val location: String,
        val requiredParticipantsCount: Int,
        val description: String? = null,
    )

    @Transactional
    fun execute(command: Command) {
        val updateAgent = userRepository.findByIdExcludeDeleted(command.userId)
        val rendezvous = rendezvousRepository.findByIdExcludeDeleted(command.rendezvousId)

        if (rendezvous.creator != updateAgent) throw IllegalAccessException("Only creator can update rendezvous.")

        rendezvous.updateByCreator(
            title = command.title,
            appointmentTime = command.appointmentTime,
            location = command.location,
            requiredParticipantsCount = command.requiredParticipantsCount,
            description = command.description,
        )
        rendezvousRepository.save(rendezvous)
    }
}
