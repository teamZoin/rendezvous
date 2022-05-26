package com.zoin.rendezvous.domain.rendezvous.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import com.zoin.rendezvous.domain.rendezvous.QRendezvous.rendezvous
import com.zoin.rendezvous.domain.rendezvous.Rendezvous
import org.springframework.stereotype.Component

@Component
class RendezvousRepositoryImpl(
    private val rendezvousJpaRepository: RendezvousJpaRepository,
    private val queryFactory: JPAQueryFactory,
) : RendezvousCustomRepository {
    override fun findByIdExcludeDeleted(id: Long): Rendezvous {
        val rendezvous = rendezvousJpaRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Rendezvous not found. id: $id") }
        if (rendezvous.deletedAt != null) throw IllegalStateException("The rendezvous(id: $id) was deleted.")
        return rendezvous
    }

    override fun findPageByCursorLimitSize(size: Long, cursorId: Long?): List<Rendezvous> {
        val predicate = BooleanBuilder(rendezvous.deletedAt.isNull)
        if (cursorId != null) predicate.and(rendezvous.id.lt(cursorId))

        return queryFactory.selectFrom(rendezvous)
            .where(predicate)
            .limit(size)
            .orderBy(rendezvous.createdAt.desc())
            .fetch()
    }

    override fun hasNextElement(endId: Long): Boolean {
        val notDeleted = BooleanBuilder(rendezvous.deletedAt.isNull)
        return queryFactory.selectFrom(rendezvous)
            .where(notDeleted.and(rendezvous.id.lt(endId)))
            .orderBy(rendezvous.createdAt.desc())
            .limit(1)
            .fetch().isNotEmpty()
    }
}
