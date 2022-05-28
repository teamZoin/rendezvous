package com.zoin.rendezvous.domain.rendezvous.repository

import com.zoin.rendezvous.domain.rendezvous.Rendezvous
import com.zoin.rendezvous.domain.rendezvous.RendezvousParticipant
import com.zoin.rendezvous.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface RendezvousParticipantJpaRepository : JpaRepository<RendezvousParticipant, Long> {
    fun findByRendezvousAndParticipant(rendezvous: Rendezvous, user: User): RendezvousParticipant?
}
