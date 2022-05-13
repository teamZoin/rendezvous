package com.zoin.rendezvous.base

import java.time.LocalDateTime

interface SoftDeletable {
    var deletedAt: LocalDateTime?
}
