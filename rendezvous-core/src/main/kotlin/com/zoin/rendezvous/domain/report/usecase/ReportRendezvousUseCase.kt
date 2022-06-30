package com.zoin.rendezvous.domain.report.usecase

import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.domain.report.RendezvousReport
import com.zoin.rendezvous.domain.report.ReportReason
import com.zoin.rendezvous.domain.report.repository.RendezvousReportRepository
import com.zoin.rendezvous.domain.user.repository.UserRepository
import javax.inject.Named

@Named
class ReportRendezvousUseCase(
    private val userRepository: UserRepository,
    private val rendezvousRepository: RendezvousRepository,
    private val rendezvousReportRepository: RendezvousReportRepository,
) {
    data class Command(
        val reporterId: Long,
        val rendezvousId: Long,
        val reportReason: ReportReason,
        val etcDescription: String?
    )

    fun execute(command: Command) {
        val user = userRepository.findByIdExcludeDeleted(command.reporterId)
        val rendezvous = rendezvousRepository.findByIdExcludeDeleted(command.rendezvousId)

        // 본인의 글일 경우
        if (rendezvous.creator == user) throw IllegalArgumentException("본인의 글을 신고할 수 없습니다.")

        // TODO: 한 유저가 글을 여러 번 신고할 수 있는지 체크
        rendezvousReportRepository.findByReporterAndRendezvous(reporter = user, rendezvous)?.let {
            throw IllegalStateException("이미 신고한 글입니다.")
        }

        if (command.reportReason == ReportReason.ETC && command.etcDescription == null) {
            throw IllegalArgumentException("신고 사유를 입력해주세요.")
        }

        rendezvousReportRepository.save(
            RendezvousReport(
                reporter = user,
                rendezvous = rendezvous,
                reportReason = command.reportReason,
                etcDescription = command.etcDescription,
            )
        )
    }
}
