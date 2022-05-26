package com.zoin.rendezvous.domain.rendezvous.repository

import com.zoin.rendezvous.domain.rendezvous.RendezvousParticipant
import org.springframework.data.jpa.repository.JpaRepository

interface RendezvousParticipantRepository : JpaRepository<RendezvousParticipant, Long>
