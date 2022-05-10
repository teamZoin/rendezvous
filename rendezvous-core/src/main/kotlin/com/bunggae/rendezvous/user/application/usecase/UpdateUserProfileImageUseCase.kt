package com.bunggae.rendezvous.user.application.usecase

import com.bunggae.rendezvous.user.application.aggregate.UserAggregate
import com.bunggae.rendezvous.user.domain.User
import javax.inject.Named

@Named
class UpdateUserProfileImageUseCase(
    private val userAggregate: UserAggregate,
) {
    data class Command(val userId: Long, val profileImgUrl: String)

    fun execute(command: Command): User {
        val(userId, profileImgUrl) = command

        val user = userAggregate.findOneOrNull(userId) ?: throw IllegalArgumentException("user not found")

        user.profileImgUrl = profileImgUrl
        userAggregate.save(user)
        return user
    }
}
