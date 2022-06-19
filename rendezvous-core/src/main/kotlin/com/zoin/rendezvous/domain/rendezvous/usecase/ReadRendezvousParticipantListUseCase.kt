package com.zoin.rendezvous.domain.rendezvous.usecase

import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.domain.user.UserVO
import com.zoin.rendezvous.domain.user.repository.UserRepository
import javax.inject.Named

@Named
class ReadRendezvousParticipantListUseCase(
    private val rendezvousRepository: RendezvousRepository,
    private val userRepository: UserRepository,
) {
    data class Query(
        val rendezvousId: Long,
        val readerId: Long,
    )

    fun execute(query: Query): List<UserVO> {
        val (rendezvousId, readerId) = query
        val rendezvous = rendezvousRepository.findByIdExcludeDeleted(rendezvousId)
        val reader = userRepository.findByIdExcludeDeleted(readerId)
        if (rendezvous.creator != reader) throw IllegalAccessException("번개 작성자가 아닙니다.")
        return rendezvous.participants.map {
            UserVO.of(it.participant)
        }
    }
}
