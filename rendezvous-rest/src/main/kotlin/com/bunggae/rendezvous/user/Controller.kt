package com.bunggae.rendezvous.user

import com.bunggae.rendezvous.common.Response
import com.bunggae.rendezvous.user.application.usecase.CreateUserUseCase
import com.bunggae.rendezvous.user.dto.UserSignUpReqDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class Controller(
    private val createUserUseCase: CreateUserUseCase,
) {
    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody req: UserSignUpReqDto,
    ): Response<String> {
        val (email, password, userName, serviceId, profileImgUrl) = req
        createUserUseCase.execute(
            CreateUserUseCase.Command(
                email = email,
                password = password,
                userName = userName,
                serviceId = serviceId,
                profileImgUrl = profileImgUrl,
            )
        )
        return Response(
            status = 200,
            message = "회원가입 성공",
        )
    }
}

