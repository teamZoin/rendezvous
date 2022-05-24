package com.zoin.rendezvous.domain.rendezvous.usecase

import com.zoin.rendezvous.domain.rendezvous.Rendezvous
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.domain.user.repository.UserRepository
import javax.inject.Named

@Named
class ReadRendezvousUseCase(
    private val rendezvousRepository: RendezvousRepository,
    private val userRepository: UserRepository,
) {
    data class Query(
        val id: Long,
        val userId: Long,
    )

    data class RendezvousAndReader(
        val rendezvous: Rendezvous,
        val isAuthor: Boolean,
    )

    fun execute(query: Query): RendezvousAndReader {
        val (rendezvousId, userId) = query
        val reader =
            userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found. id: $userId") }
        val rendezvous = rendezvousRepository.findById(rendezvousId)
            .orElseThrow { IllegalArgumentException("Rendezvous not found. id: $rendezvousId") }
        return RendezvousAndReader(
            rendezvous,
            reader == rendezvous.creator
        )
    }
}
