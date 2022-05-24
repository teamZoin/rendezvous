package com.zoin.rendezvous.api.rendezvous

import com.zoin.rendezvous.api.`interface`.Response
import com.zoin.rendezvous.api.`interface`.dto.GetMainReqDto
import com.zoin.rendezvous.api.`interface`.dto.SaveRendezvousReqDto
import com.zoin.rendezvous.domain.PageByCursor
import com.zoin.rendezvous.domain.rendezvous.RendezvousVO
import com.zoin.rendezvous.domain.rendezvous.usecase.CreateRendezvousUseCase
import com.zoin.rendezvous.domain.rendezvous.usecase.DeleteRendezvousUseCase
import com.zoin.rendezvous.domain.rendezvous.usecase.ReadRendezvousListUseCase
import com.zoin.rendezvous.domain.rendezvous.usecase.ReadRendezvousUseCase
import com.zoin.rendezvous.domain.rendezvous.usecase.UpdateRendezvousUseCase
import com.zoin.rendezvous.resolver.AuthTokenPayload
import com.zoin.rendezvous.util.authToken.TokenPayload
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rendezvous")
class RendezvousController(
    private val createRendezvousUseCase: CreateRendezvousUseCase,
    private val readRendezvousUseCase: ReadRendezvousUseCase,
    private val updateRendezvousUseCase: UpdateRendezvousUseCase,
    private val deleteRendezvousUseCase: DeleteRendezvousUseCase,
    private val readRendezvousListUseCase: ReadRendezvousListUseCase,
) {
    @PostMapping("")
    fun createRendezvous(
        @AuthTokenPayload payload: TokenPayload,
        @RequestBody req: SaveRendezvousReqDto,
    ): Response<Unit> {
        val (userId) = payload

        createRendezvousUseCase.execute(
            command = CreateRendezvousUseCase.Command(
                creatorId = userId,
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

    @GetMapping("/{id}")
    fun getRendezvous(
        @AuthTokenPayload payload: TokenPayload,
        @PathVariable(value = "id") rendezvousId: Long,
    ): Response<ReadRendezvousUseCase.RendezvousAndReader> {
        val rendezvousAndReader = readRendezvousUseCase.execute(
            ReadRendezvousUseCase.Query(
                rendezvousId,
                payload.userId
            )
        )
        return Response(
            status = HttpStatus.OK.value(),
            message = "번개 조회 성공",
            data = rendezvousAndReader
        )
    }

    @PutMapping("/{id}")
    fun putRendezvous(
        @AuthTokenPayload payload: TokenPayload,
        @PathVariable(value = "id") rendezvousId: Long,
        @RequestBody updateRendezvousReq: SaveRendezvousReqDto,
    ): Response<Unit> {
        updateRendezvousUseCase.execute(
            UpdateRendezvousUseCase.Command(
                userId = payload.userId,
                rendezvousId = rendezvousId,
                title = updateRendezvousReq.title,
                appointmentTime = updateRendezvousReq.appointmentTime,
                location = updateRendezvousReq.location,
                requiredParticipantsCount = updateRendezvousReq.requiredParticipantsCount,
                description = updateRendezvousReq.description,
            )
        )
        return Response(
            message = "번개 업데이트 성공",
        )
    }

    @DeleteMapping("/{id}")
    fun deleteRendezvous(
        @AuthTokenPayload payload: TokenPayload,
        @PathVariable(value = "id") rendezvousId: Long,
    ): Response<Unit> {
        deleteRendezvousUseCase.execute(
            DeleteRendezvousUseCase.Command(
                userId = payload.userId,
                rendezvousId = rendezvousId,
            )
        )
        return Response(
            message = "번개 삭제 성공"
        )
    }

    @GetMapping("/main")
    fun getMain(
        @AuthTokenPayload payload: TokenPayload,
        @RequestBody getMainReqDto: GetMainReqDto,
    ): Response<PageByCursor<RendezvousVO>> {
        val (size, cursorId) = getMainReqDto
        val page = readRendezvousListUseCase.execute(
            ReadRendezvousListUseCase.Query(
                size,
                cursorId,
            )
        )
        return Response(
            message = "메인 번개 리스트 조회 성공",
            data = page,
        )
    }
}
