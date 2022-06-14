package com.zoin.rendezvous.api.friend

import com.zoin.rendezvous.api.common.Response
import com.zoin.rendezvous.api.friend.dto.AcceptFriendRequestDto
import com.zoin.rendezvous.api.friend.dto.CreateFriendRequestDto
import com.zoin.rendezvous.domain.friend.usecase.AcceptFriendRequestUseCase
import com.zoin.rendezvous.domain.friend.usecase.CreateFriendRequestUseCase
import com.zoin.rendezvous.resolver.AuthTokenPayload
import com.zoin.rendezvous.util.authToken.TokenPayload
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/friend")
class FriendController(
    private val createFriendRequestUseCase: CreateFriendRequestUseCase,
    private val acceptFriendRequestUseCase: AcceptFriendRequestUseCase,
) {

    @PostMapping
    fun createFriendRequest(
        @AuthTokenPayload payload: TokenPayload,
        @RequestBody req: CreateFriendRequestDto,
    ): Response<Unit> {
        createFriendRequestUseCase.execute(
            CreateFriendRequestUseCase.Command(
                userId = payload.userId,
                targetUserId = req.targetUserId,
            )
        )
        return Response(
            message = "친구 신청 성공"
        )
    }

    @PutMapping
    fun acceptFriendRequest(
        @AuthTokenPayload payload: TokenPayload,
        @RequestBody req: AcceptFriendRequestDto,
    ): Response<Unit> {
        acceptFriendRequestUseCase.execute(
            AcceptFriendRequestUseCase.Command(
                targetUserId = payload.userId,
                requesterId = req.requesterId,
            )
        )
        return Response(
            message = "친구 신청 수락 성공"
        )
    }
}
