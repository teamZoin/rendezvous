package com.zoin.rendezvous.domain.rendezvous.usecase

import com.zoin.rendezvous.domain.rendezvous.Rendezvous
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.domain.user.repository.UserRepository
import javax.inject.Named

@Named
class ReadRendezvousCreatedByUserUseCase(
    private val userRepository: UserRepository,
    private val rendezvousRepository: RendezvousRepository,
) {
    data class Query(
        val userId: Long,
        val isClosed: Boolean,
        val size: Long,
        val cursorId: Long?,
    )

    fun execute(query: Query): List<Rendezvous> {
        val (userId, isClosed, size, cursorId) = query
        val user = userRepository.findByIdExcludeDeleted(userId)
        return rendezvousRepository.findByCreator(user, isClosed, size, cursorId)
    }
}
