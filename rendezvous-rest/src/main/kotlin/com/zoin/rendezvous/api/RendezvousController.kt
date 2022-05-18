package com.zoin.rendezvous.api

import com.zoin.rendezvous.api.`interface`.Response
import com.zoin.rendezvous.api.`interface`.dto.CreateRendezvousReqDto
import com.zoin.rendezvous.domain.rendezvous.usecase.CreateRendezvousUseCase
import com.zoin.rendezvous.resolver.AuthTokenPayload
import com.zoin.rendezvous.util.authToken.TokenPayload
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rendezvous")
class RendezvousController(
    private val createRendezvousUseCase: CreateRendezvousUseCase,

) {
    @PostMapping("")
    fun createRendezvous(
        @AuthTokenPayload payload: TokenPayload,
        @RequestBody req: CreateRendezvousReqDto,
    ): Response<Unit> {
        val (userId) = payload

        createRendezvousUseCase.execute(
            command = CreateRendezvousUseCase.Command(
                userId = userId,
                title = req.title,
                appointmentTime = req.appointmentTime,
                location = req.location,
                requiredParticipantsCount = req.requiredParticipantsCount,
                description = req.description,
            )
        )

        return Response(
            status = HttpStatus.OK.value(),
            message = "성공"
        )
    }
}
