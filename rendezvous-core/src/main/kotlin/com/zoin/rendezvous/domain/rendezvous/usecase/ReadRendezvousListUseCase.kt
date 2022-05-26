package com.zoin.rendezvous.domain.rendezvous.usecase

import com.zoin.rendezvous.domain.rendezvous.Rendezvous
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

    fun execute(query: Query): Pair<List<Rendezvous>, Boolean> {
        val rendezvousList = rendezvousRepository.findPageByCursorLimitSize(query.size, query.cursorId)
        val hasNext = rendezvousList.isNotEmpty() && rendezvousRepository.hasNextElement(rendezvousList[rendezvousList.size - 1].mustGetId())
        return rendezvousList to hasNext
    }
}
