package com.zoin.rendezvous.domain.friend.usecase

import com.zoin.rendezvous.domain.friend.Friend
import com.zoin.rendezvous.domain.friend.Status
import com.zoin.rendezvous.domain.friend.repository.FriendRepository
import com.zoin.rendezvous.domain.user.repository.UserRepository
import javax.inject.Named

@Named
class CreateFriendRequestUseCase(
    private val userRepository: UserRepository,
    private val friendRepository: FriendRepository,
) {
    data class Command(
        val userId: Long,
        val targetUserId: Long,
    )

    fun execute(command: Command) {
        val user = userRepository.findByIdExcludeDeleted(command.userId)
        val targetUser = userRepository.findByIdExcludeDeleted(command.targetUserId)
        if (friendRepository.ifUsersAreAlreadyFriend(user, targetUser)) {
            throw IllegalStateException("이미 친구목록에 있는 친구입니다.")
        }
        val newFriendRequest = Friend(user, targetUser, Status.PENDING)
        friendRepository.save(newFriendRequest)
    }
}
