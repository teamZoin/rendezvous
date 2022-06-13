package com.zoin.rendezvous.domain.user.usecase

import com.zoin.rendezvous.domain.user.User
import com.zoin.rendezvous.domain.user.repository.UserRepository
import javax.inject.Named
import javax.transaction.Transactional

@Named
class UpdateUserProfileUseCase(
    private val userRepository: UserRepository,
) {
    data class Command(
        val userId: Long,
        val newUserName: String?,
        val newProfileImgUrl: String?,
    )

    @Transactional
    fun execute(command: Command): User {
        val user = userRepository.findByIdExcludeDeleted(command.userId)
        if (command.newUserName != null)
            user.changeUsername(command.newUserName)
        if (command.newProfileImgUrl != null)
            user.changeProfileImg(command.newProfileImgUrl)
        return user
    }
}
