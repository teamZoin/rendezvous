package com.zoin.rendezvous.domain.rendezvous.repository

import com.zoin.rendezvous.domain.rendezvous.Rendezvous

interface RendezvousCustomRepository {
    fun findByIdExcludeDeleted(id: Long): Rendezvous
}
