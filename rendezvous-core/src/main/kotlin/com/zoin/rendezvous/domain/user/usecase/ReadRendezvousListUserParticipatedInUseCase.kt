package com.zoin.rendezvous.domain.user.usecase

import com.zoin.rendezvous.domain.rendezvous.Rendezvous
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.domain.user.repository.UserRepository
import javax.inject.Named

@Named
class ReadRendezvousListUserParticipatedInUseCase(
    private val rendezvousRepository: RendezvousRepository,
    private val userRepository: UserRepository,
) {
    data class Query(
        val userId: Long,
        val isClosed: Boolean,
        val size: Long,
        val cursor: Long? = null,
    )

    fun execute(query: Query): List<Rendezvous> {
        val user = userRepository.findByIdExcludeDeleted(query.userId)
        return rendezvousRepository.findByParticipant(user, query.isClosed, query.size, query.cursor)
    }
}
