package com.zoin.rendezvous.domain.rendezvous.repository

import com.zoin.rendezvous.domain.SoftDeletableEntityRepository
import com.zoin.rendezvous.domain.rendezvous.Rendezvous
import com.zoin.rendezvous.domain.user.User

interface RendezvousCustomRepository : SoftDeletableEntityRepository<Rendezvous, Long> {
    fun findPageByCursorLimitSize(size: Long, cursorId: Long?): List<Rendezvous>
    fun hasNextElement(endId: Long): Boolean
    fun findByCreator(creator: User, isClosed: Boolean, size: Long, cursorId: Long?): List<Rendezvous>
    fun findByParticipant(participant: User, isClosed: Boolean, size: Long, cursorId: Long?): List<Rendezvous>
}
