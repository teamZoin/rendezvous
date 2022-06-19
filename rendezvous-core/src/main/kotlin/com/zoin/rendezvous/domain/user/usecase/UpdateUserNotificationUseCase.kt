package com.zoin.rendezvous.domain.user.usecase

import com.zoin.rendezvous.domain.user.repository.UserRepository
import javax.inject.Named

@Named
class UpdateUserNotificationUseCase(
    private val userRepository: UserRepository,
) {
    data class Command(
        val userId: Long,
        val on: Boolean,
    )

    fun execute(command: Command) {
        val user = userRepository.findByIdExcludeDeleted(command.userId)

        if (command.on) {
            user.agreeToGetNotification()
        } else {
            user.disagreeToGetNotification()
        }
        userRepository.save(user)
    }
}
