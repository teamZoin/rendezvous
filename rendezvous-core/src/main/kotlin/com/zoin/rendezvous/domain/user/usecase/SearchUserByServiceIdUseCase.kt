package com.zoin.rendezvous.domain.user.usecase

import com.zoin.rendezvous.domain.friend.UserRelationship
import com.zoin.rendezvous.domain.friend.repository.FriendRepository
import com.zoin.rendezvous.domain.user.User
import com.zoin.rendezvous.domain.user.repository.UserRepository
import javax.inject.Named

@Named
class SearchUserByServiceIdUseCase(
    private val userRepository: UserRepository,
    private val friendRepository: FriendRepository,
) {
    data class Query(
        val userId: Long,
        val searchIdInput: String,
    )

    fun execute(query: Query): List<Pair<User, UserRelationship>> {
        val actor = userRepository.findByIdExcludeDeleted(query.userId)
        return userRepository.findByServiceIdContaining(query.searchIdInput).map { searchedUser ->
            val friendRequest =
                friendRepository.findByUserAndFriend(actor, searchedUser)
                    ?: return@map searchedUser to UserRelationship.NOT_FRIEND
            if (friendRequest.isFriend()) {
                searchedUser to UserRelationship.FRIEND
            } else {
                if (friendRequest.user == actor) {
                    searchedUser to UserRelationship.WAITING
                } else {
                    searchedUser to UserRelationship.HOLDING
                }
            }
        }
    }
}
