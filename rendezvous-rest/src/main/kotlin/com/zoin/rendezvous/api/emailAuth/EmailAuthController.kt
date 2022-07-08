package com.zoin.rendezvous.api.emailAuth

import com.zoin.rendezvous.api.common.Response
import com.zoin.rendezvous.api.emailAuth.dto.MakeMockEmailValidReqDto
import com.zoin.rendezvous.api.emailAuth.dto.VerifyCodeReqDto
import com.zoin.rendezvous.api.user.dto.VerifyEmailReqDto
import com.zoin.rendezvous.domain.emailAuth.usecase.MakeMockEmailValidUseCase
import com.zoin.rendezvous.domain.emailAuth.usecase.VerifyEmailByCodeUseCase
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/email-auth")
class EmailAuthController(
    private val makeMockEmailValidUseCase: MakeMockEmailValidUseCase,
    private val verifyEmailByCodeUseCase: VerifyEmailByCodeUseCase,
) {
    @ConditionalOnProperty(name = ["mail.mock"], havingValue = "true")
    @PatchMapping("/mock")
    fun makeMockEmailValid(
        @RequestBody req: MakeMockEmailValidReqDto,
    ): Response<Unit> {
        makeMockEmailValidUseCase.execute(
            MakeMockEmailValidUseCase.Command(req.email)
        )
        return Response(
            message = "테스트 메일 인증 처리 완료"
        )
    }

    @PatchMapping
    fun verifyCode(
        @RequestBody req: VerifyCodeReqDto,
    ): Response<Any> {
        verifyEmailByCodeUseCase.execute(
            VerifyEmailByCodeUseCase.Query(
                email = req.email,
                code = req.code,
            )
        )
        return Response(
            message = "이메일 인증 성공"
        )
    }
}
