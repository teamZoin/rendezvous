package com.zoin.rendezvous.domain

interface SoftDeletableEntityRepository<T, ID> {
    fun findByIdExcludeDeleted(id: ID): T
}
