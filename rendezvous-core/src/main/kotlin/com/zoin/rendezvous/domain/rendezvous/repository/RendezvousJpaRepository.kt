package com.zoin.rendezvous.domain.rendezvous.repository

import com.zoin.rendezvous.domain.rendezvous.Rendezvous
import org.springframework.data.jpa.repository.JpaRepository

interface RendezvousJpaRepository : JpaRepository<Rendezvous, Long>
