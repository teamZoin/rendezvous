package com.zoin.rendezvous.domain.report.repository

import com.zoin.rendezvous.domain.rendezvous.Rendezvous
import com.zoin.rendezvous.domain.report.RendezvousReport
import com.zoin.rendezvous.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface ReportJpaRepository : JpaRepository<RendezvousReport, Long> {
    fun findByReporterAndRendezvous(reporter: User, rendezvous: Rendezvous): RendezvousReport?
}
