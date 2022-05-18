package com.bunggae.rendezvous.user.application.usecase

import com.bunggae.rendezvous.user.application.aggregate.UserAggregate
import javax.inject.Named

@Named
class UpdateUserNotificationUseCase(
    private val userAggregate: UserAggregate,
) {
    data class Command(
        val userId: Long,
        val on: Boolean,
    )

    fun execute(command: Command) {
        val user = userAggregate.findOneOrNull(command.userId)
            ?: throw IllegalStateException("User not found. userId: ${command.userId}")

        if (command.on) {
            user.agreeToGetNotification()
        } else {
            user.disagreeToGetNotification()
        }
        userAggregate.save(user)
    }
}
