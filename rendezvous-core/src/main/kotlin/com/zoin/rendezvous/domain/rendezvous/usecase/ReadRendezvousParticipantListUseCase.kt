package com.zoin.rendezvous.domain.rendezvous.usecase

import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousParticipantRepository
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.domain.user.UserVO
import com.zoin.rendezvous.domain.user.repository.UserRepository
import javax.inject.Named

@Named
class ReadRendezvousParticipantListUseCase(
    private val rendezvousRepository: RendezvousRepository,
    private val userRepository: UserRepository,
    private val rendezvousParticipantRepository: RendezvousParticipantRepository,
) {
    data class Query(
        val rendezvousId: Long,
        val readerId: Long,
    )
    fun execute(query: Query): List<UserVO> {
        val (rendezvousId, renderId) = query
        val rendezvous = rendezvousRepository.findByIdExcludeDeleted(rendezvousId)
        val reader = userRepository.findByIdExcludeDeleted(renderId)
        if (rendezvous.creator != reader) throw IllegalAccessException("참여자는 번개 주최자만 볼 수 있어요.")
        return rendezvous.participants.map {
            UserVO.of(it.participant)
        }
    }
}
