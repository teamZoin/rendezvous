package com.bunggae.rendezvous.w

import java.time.LocalDateTime
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class JpaBaseEntity(
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now()
)