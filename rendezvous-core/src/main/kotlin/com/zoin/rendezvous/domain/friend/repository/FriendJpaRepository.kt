package com.zoin.rendezvous.domain.friend.repository

import com.zoin.rendezvous.domain.friend.Friend
import com.zoin.rendezvous.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface FriendJpaRepository : JpaRepository<Friend, Long> {
    fun findByUserAndFriend(user: User, friend: User): Friend?
}
