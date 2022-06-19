package com.zoin.rendezvous.domain.friend.usecase

import com.zoin.rendezvous.domain.friend.repository.FriendRepository
import com.zoin.rendezvous.domain.user.repository.UserRepository
import javax.inject.Named
import javax.transaction.Transactional

@Named
class AcceptFriendRequestUseCase(
    private val userRepository: UserRepository,
    private val friendRepository: FriendRepository,
) {
    data class Command(
        val targetUserId: Long,
        val requesterId: Long,
    )

    @Transactional
    fun execute(command: Command) {
        val requester = userRepository.findByIdExcludeDeleted(command.requesterId)
        val targetUser = userRepository.findByIdExcludeDeleted(command.targetUserId)
        val relationship = friendRepository.findByUserAndFriend(requester, targetUser)
            ?: throw IllegalArgumentException("해당하는 친구 신청이 존재하지 않습니다.")
        if (relationship.isFriend()) {
            throw kotlin.IllegalStateException("이미 친구관계입니다.")
        }
        relationship.acceptFriendRequest()
    }
}
