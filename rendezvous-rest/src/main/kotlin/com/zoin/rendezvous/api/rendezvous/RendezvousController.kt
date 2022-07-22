package com.zoin.rendezvous.api.rendezvous

import com.zoin.rendezvous.api.common.PageByCursor
import com.zoin.rendezvous.api.common.Response
import com.zoin.rendezvous.api.rendezvous.dto.SaveRendezvousReqDto
import com.zoin.rendezvous.domain.rendezvous.RendezvousVO
import com.zoin.rendezvous.domain.rendezvous.usecase.AddParticipantUseCase
import com.zoin.rendezvous.domain.rendezvous.usecase.CloseRendezvousUseCase
import com.zoin.rendezvous.domain.rendezvous.usecase.CreateRendezvousUseCase
import com.zoin.rendezvous.domain.rendezvous.usecase.DeleteParticipantUseCase
import com.zoin.rendezvous.domain.rendezvous.usecase.DeleteRendezvousUseCase
import com.zoin.rendezvous.domain.rendezvous.usecase.GetCurrentRendezvousStatusUseCase
import com.zoin.rendezvous.domain.rendezvous.usecase.ReadRendezvousListUseCase
import com.zoin.rendezvous.domain.rendezvous.usecase.ReadRendezvousParticipantListUseCase
import com.zoin.rendezvous.domain.rendezvous.usecase.ReadRendezvousUseCase
import com.zoin.rendezvous.domain.rendezvous.usecase.UpdateRendezvousUseCase
import com.zoin.rendezvous.domain.user.UserVO
import com.zoin.rendezvous.resolver.AuthTokenPayload
import com.zoin.rendezvous.util.authToken.TokenPayload
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rendezvous")
class RendezvousController(
    private val createRendezvousUseCase: CreateRendezvousUseCase,
    private val readRendezvousUseCase: ReadRendezvousUseCase,
    private val updateRendezvousUseCase: UpdateRendezvousUseCase,
    private val deleteRendezvousUseCase: DeleteRendezvousUseCase,
    private val readRendezvousListUseCase: ReadRendezvousListUseCase,
    private val addParticipantUseCase: AddParticipantUseCase,
    private val deleteParticipantUseCase: DeleteParticipantUseCase,
    private val readRendezvousParticipantListUseCase: ReadRendezvousParticipantListUseCase,
    private val closeRendezvousUseCase: CloseRendezvousUseCase,
    private val getCurrentRendezvousStatusUseCase: GetCurrentRendezvousStatusUseCase,
) {
    @GetMapping("/current")
    fun getCurrentStatus(
        @AuthTokenPayload payload: TokenPayload,
    ): Response<Int> {
        val res: GetCurrentRendezvousStatusUseCase.CurrentStatus =
            getCurrentRendezvousStatusUseCase.execute(GetCurrentRendezvousStatusUseCase.Query(payload.userId))
        return Response(
            message = "번개 홈 상단 날씨 조회 성공",
            data = res.ordinal
        )
    }

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
        @RequestParam(value = "size") size: Long,
        @RequestParam(value = "cursor") cursorId: Long? = null,
    ): Response<PageByCursor<RendezvousVO>> {
        val (rendezvousList, hasNext) = readRendezvousListUseCase.execute(
            ReadRendezvousListUseCase.Query(
                size,
                cursorId,
            )
        )
        return Response(
            message = "메인 번개 리스트 조회 성공",
            data = PageByCursor(
                elements = rendezvousList.map { rendezvous ->
                    RendezvousVO.of(rendezvous, true).let {
                        it.addCreatorIdentity(payload.userId)
                        it
                    }
                },
                hasNext
            ),
        )
    }

    @PostMapping("/{id}/participant")
    fun addParticipant(
        @AuthTokenPayload tokenPayload: TokenPayload,
        @PathVariable(value = "id") rendezvousId: Long,
    ): Response<Unit> {
        addParticipantUseCase.execute(
            AddParticipantUseCase.Command(
                rendezvousId = rendezvousId,
                userId = tokenPayload.userId
            )
        )
        return Response(
            message = "번개 참여 요청 성공"
        )
    }

    @DeleteMapping("/{id}/participant")
    fun deleteParticipant(
        @AuthTokenPayload tokenPayload: TokenPayload,
        @PathVariable(value = "id") rendezvousId: Long,
    ): Response<Unit> {
        deleteParticipantUseCase.execute(
            DeleteParticipantUseCase.Command(
                rendezvousId = rendezvousId,
                userId = tokenPayload.userId,
            )
        )

        return Response(
            message = "번개 참여 취소 성공"
        )
    }

    @GetMapping("/{id}/participants")
    fun readParticipantList(
        @AuthTokenPayload tokenPayload: TokenPayload,
        @PathVariable(value = "id") rendezvousId: Long,
    ): Response<List<UserVO>> {
        val participantList = readRendezvousParticipantListUseCase.execute(
            ReadRendezvousParticipantListUseCase.Query(
                rendezvousId = rendezvousId,
                readerId = tokenPayload.userId,
            )
        )
        return Response(
            message = "번개 참여자 목록 조회 성공",
            data = participantList,
        )
    }

    @PatchMapping("/{id}/close")
    fun closeRendezvous(
        @AuthTokenPayload tokenPayload: TokenPayload,
        @PathVariable(value = "id") rendezvousId: Long,
    ): Response<Unit> {
        closeRendezvousUseCase.execute(
            CloseRendezvousUseCase.Command(
                userId = tokenPayload.userId,
                rendezvousId = rendezvousId,
            )
        )
        return Response(
            message = "번개 마감 성공"
        )
    }
}
