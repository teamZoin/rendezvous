package com.zoin.rendezvous.domain.rendezvous.usecase

import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousParticipantRepository
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.domain.user.repository.UserRepository
import javax.inject.Named
import javax.transaction.Transactional

@Named
class DeleteParticipantUseCase(
    private val rendezvousRepository: RendezvousRepository,
    private val userRepository: UserRepository,
    private val rendezvousParticipantRepository: RendezvousParticipantRepository,
) {
    data class Command(
        val userId: Long,
        val rendezvousId: Long,
    )

    @Transactional
    fun execute(command: Command) {
        val (userId, rendezvousId) = command
        val user = userRepository.findByIdExcludeDeleted(userId)
        val rendezvous = rendezvousRepository.findByIdExcludeDeleted(rendezvousId)
        if (user == rendezvous.creator) throw IllegalStateException("작성자는 반드시 번개에 참여해야합니다.")
        rendezvous.deleteNewParticipant(user, rendezvousParticipantRepository)
        rendezvousRepository.save(rendezvous)
    }
}
