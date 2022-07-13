package com.zoin.rendezvous.domain.rendezvous.repository

import com.zoin.rendezvous.domain.rendezvous.Rendezvous
import com.zoin.rendezvous.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface RendezvousJpaRepository : JpaRepository<Rendezvous, Long> {
    fun countByCreatorInAndCreatedAtAfter(creator: List<User>, after: LocalDateTime): Long
}
