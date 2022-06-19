package com.zoin.rendezvous.domain.friend.repository

import com.zoin.rendezvous.domain.user.User

interface FriendCustomRepository {
    fun ifUsersAreAlreadyFriend(user: User, other: User): Boolean
}
