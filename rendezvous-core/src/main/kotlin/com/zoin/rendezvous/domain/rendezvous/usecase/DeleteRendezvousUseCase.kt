package com.zoin.rendezvous.domain.rendezvous.usecase

import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.domain.user.repository.UserRepository
import javax.inject.Named

@Named
class DeleteRendezvousUseCase(
    private val userRepository: UserRepository,
    private val rendezvousRepository: RendezvousRepository,
) {
    data class Command(
        val userId: Long,
        val rendezvousId: Long,
    )

    fun execute(command: Command) {
        val deleteAgent = userRepository.findByIdExcludeDeleted(command.userId)
        val rendezvous = rendezvousRepository.findByIdExcludeDeleted(command.rendezvousId)

        if (rendezvous.creator != deleteAgent) throw IllegalAccessException("Only creator can delete reandezvous.")

        deleteAgent.deleteRendezvous(rendezvousRepository, rendezvous)
    }
}
