package com.zoin.rendezvous.domain.user.usecase

import com.zoin.rendezvous.domain.user.QuitReason
import com.zoin.rendezvous.domain.user.UserQuitLog
import com.zoin.rendezvous.domain.user.repository.UserQuitLogRepository
import com.zoin.rendezvous.domain.user.repository.UserRepository
import com.zoin.rendezvous.infra.MailService
import javax.inject.Named
import javax.transaction.Transactional

@Named
class QuitServiceUseCase(
    private val userRepository: UserRepository,
    private val userQuitLogRepository: UserQuitLogRepository,
    private val mailService: MailService,
) {
    data class Command(
        val userId: Long,
        val quitReason: QuitReason,
        val etcDesc: String?,
    )

    @Transactional
    fun execute(command: Command) {
        val user = userRepository.findByIdExcludeDeleted(command.userId)

        // 기타일 경우 사유가 있어야함
        if(command.quitReason == QuitReason.ETC && command.etcDesc == null) {
            throw IllegalArgumentException("탈퇴 사유를 입력해주세요.")
        }

        user.quitService(userRepository)

        userQuitLogRepository.save(
            UserQuitLog(
                user = user,
                reason = command.quitReason,
                etcDescription = command.etcDesc
            )
        ).also { mailService.sendQuitLog(it) }

    }
}
