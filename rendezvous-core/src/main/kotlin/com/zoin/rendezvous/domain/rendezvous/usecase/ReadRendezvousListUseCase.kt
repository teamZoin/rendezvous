package com.zoin.rendezvous.domain.rendezvous.usecase

import com.zoin.rendezvous.domain.PageByCursor
import com.zoin.rendezvous.domain.rendezvous.RendezvousVO
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import javax.inject.Named

@Named
class ReadRendezvousListUseCase(
    private val rendezvousRepository: RendezvousRepository,
) {
    data class Query(
        val size: Long,
        val cursorId: Long? = null,
    )

    fun execute(query: Query): PageByCursor<RendezvousVO> {
        val rendezvous = rendezvousRepository.findPageByCursorLimitSize(query.size, query.cursorId)
        return PageByCursor(
            rendezvous.map {
                RendezvousVO.of(it, true)
            },
            rendezvous.isNotEmpty() && rendezvousRepository.hasNextElement(rendezvous[rendezvous.size - 1].mustGetId())
        )
    }
}
