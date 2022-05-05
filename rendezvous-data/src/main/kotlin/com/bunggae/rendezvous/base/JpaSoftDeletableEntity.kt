package com.bunggae.rendezvous.base

import com.bunggae.rendezvous.w.JpaBaseEntity
import java.time.LocalDateTime

open class JpaSoftDeletableEntity(
    override var deletedAt: LocalDateTime? = null,
): JpaBaseEntity(), SoftDeletable