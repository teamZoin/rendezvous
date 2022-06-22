package com.zoin.rendezvous.api.report

import com.zoin.rendezvous.api.common.Response
import com.zoin.rendezvous.api.report.dto.ReportRendezvousReqDto
import com.zoin.rendezvous.domain.report.ReportReason
import com.zoin.rendezvous.domain.report.usecase.ReportRendezvousUseCase
import com.zoin.rendezvous.resolver.AuthTokenPayload
import com.zoin.rendezvous.util.authToken.TokenPayload
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/report")
class ReportController(
    private val reportRendezvousUseCase: ReportRendezvousUseCase,
) {
    @PostMapping("/rendezvous")
    fun reportRendezvous(
        @AuthTokenPayload tokenPayload: TokenPayload,
        @RequestBody req: ReportRendezvousReqDto,
    ): Response<Unit> {
        reportRendezvousUseCase.execute(
            ReportRendezvousUseCase.Command(
                reporterId = tokenPayload.userId,
                rendezvousId = req.rendezvousId,
                reportReason = ReportReason.findByDesc(req.reportReason) ?: throw IllegalArgumentException("올바르지 않은 신고 사유"),
                etcDescription = req.etcDesc,
            )
        )
        return Response(
            message = "게시물 신고 성공"
        )
    }
}
