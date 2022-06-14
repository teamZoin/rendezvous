package com.zoin.rendezvous.domain.friend.usecase

import com.zoin.rendezvous.domain.friend.Friend
import com.zoin.rendezvous.domain.friend.Status
import com.zoin.rendezvous.domain.friend.repository.FriendRepository
import com.zoin.rendezvous.domain.user.User
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

        ifUsersAreAlreadyFriendThrowsException(user, targetUser)
        val newFriendRequest = Friend(user, targetUser, Status.PENDING)
        friendRepository.save(newFriendRequest)
    }

    fun ifUsersAreAlreadyFriendThrowsException(user: User, targetUser: User) {
        // 이미 친구인 경우
        if (friendRepository.ifUsersAreAlreadyFriend(user, targetUser)) {
            throw IllegalStateException("이미 친구목록에 있는 친구입니다.")
        }

        // 이미 신청을 한 사람인 경우
        friendRepository.findByUserAndFriend(user, targetUser)?.let {
            throw IllegalStateException("이미 친구 신청을 보냈습니다.")
        }

        // 내가 수락을 하지 않은 경우
        friendRepository.findByUserAndFriend(targetUser, user)?.let {
            throw IllegalStateException("나에게 이미 친구 신청을 보낸 유저입니다.")
        }
    }
}
