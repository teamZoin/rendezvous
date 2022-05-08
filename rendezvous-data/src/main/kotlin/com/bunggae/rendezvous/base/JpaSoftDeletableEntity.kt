package com.bunggae.rendezvous.base

import java.time.LocalDateTime

open class JpaSoftDeletableEntity(
    override var deletedAt: LocalDateTime? = null,
): JpaBaseEntity(), SoftDeletable