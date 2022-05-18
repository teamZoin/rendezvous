package com.zoin.rendezvous.domain.user.usecase

import com.zoin.rendezvous.domain.user.User
import com.zoin.rendezvous.domain.user.repository.UserRepository
import javax.inject.Named

@Named
class UpdateUserProfileImageUseCase(
    private val userAggregate: UserRepository,
) {
    data class Command(val userId: Long, val profileImgUrl: String)

    fun execute(command: Command): User {
        val (userId, profileImgUrl) = command

        val user = userAggregate.findById(userId).orElseThrow { IllegalArgumentException("user not found") }

        user.changeProfileImg(profileImgUrl)
        userAggregate.save(user)
        return user
    }
}
