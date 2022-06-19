package com.zoin.rendezvous.domain.rendezvous.repository

import com.zoin.rendezvous.domain.SoftDeletableEntityRepository
import com.zoin.rendezvous.domain.rendezvous.Rendezvous

interface RendezvousCustomRepository : SoftDeletableEntityRepository<Rendezvous, Long> {
    fun findPageByCursorLimitSize(size: Long, cursorId: Long?): List<Rendezvous>
    fun hasNextElement(endId: Long): Boolean
}
