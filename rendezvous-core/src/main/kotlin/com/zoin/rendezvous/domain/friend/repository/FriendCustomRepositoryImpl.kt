package com.zoin.rendezvous.domain.friend.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.zoin.rendezvous.domain.friend.QFriend.friend1
import com.zoin.rendezvous.domain.user.User

class FriendCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : FriendCustomRepository {

    override fun ifUsersAreAlreadyFriend(user: User, other: User): Boolean {
        return queryFactory.selectFrom(friend1)
            .where(
                friend1.user.eq(user).and(friend1.friend.eq(other))
                    .or(friend1.user.eq(other).and(friend1.friend.eq(user)))
            )
            .fetch()
            .isNotEmpty()
    }
}
