package com.zoin.rendezvous.domain.user.repository

import com.zoin.rendezvous.domain.user.UserQuitLog
import org.springframework.data.jpa.repository.JpaRepository

interface UserQuitLogRepository : JpaRepository<UserQuitLog, Long> {
}
