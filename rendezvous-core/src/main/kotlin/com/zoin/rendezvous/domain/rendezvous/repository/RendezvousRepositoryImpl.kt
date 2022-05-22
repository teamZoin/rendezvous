package com.zoin.rendezvous.domain.rendezvous.repository

import com.zoin.rendezvous.domain.rendezvous.Rendezvous
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException

@Component
class RendezvousRepositoryImpl(
    private val rendezvousJpaRepository: RendezvousJpaRepository,
) : RendezvousCustomRepository {
    override fun findByIdExcludeDeleted(id: Long): Rendezvous {
        val rendezvous = rendezvousJpaRepository.findById(id).orElseThrow { IllegalArgumentException("Rendezvous not found. id: $id") }
        if (rendezvous.deletedAt != null) throw IllegalStateException("The rendezvous(id: $id) was deleted.")
        return rendezvous
    }
}
