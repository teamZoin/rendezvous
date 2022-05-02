package com.bunggae.rendezvous.user

import com.bunggae.rendezvous.common.Response
import com.bunggae.rendezvous.user.application.usecase.*
import com.bunggae.rendezvous.user.dto.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class Controller(
    private val createUserUseCase: CreateUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val checkAlreadyExistingEmailUseCase: CheckAlreadyExistingEmailUseCase,
    private val checkAlreadyExistingServiceIdUseCase: CheckAlreadyExistingServiceIdUseCase,
    private val sendVerificationEmailUseCase: SendVerificationEmailUseCase,
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
            status = HttpStatus.OK.value(),
            message = "회원가입 성공",
        )
    }

    @PostMapping("/log-in")
    fun logIn(
        @RequestBody req: UserLogInReqDto,
    ): Response<String> {
        val (email, password) = req
        val user = loginUserUseCase.execute(
            LoginUserUseCase.Query(
                email = email,
                password = password,
            )
        )
        return Response(
            status = HttpStatus.OK.value(),
            message = "로그인 성공",
        )
    }

    @PostMapping("/existing/email")
    fun checkExistingEmail(
        @RequestBody req: CheckExitingEmailReqDto,
    ): Response<Unit> {
        val query = CheckAlreadyExistingEmailUseCase.Query(req.email)
        val emailAlreadyExists = checkAlreadyExistingEmailUseCase.execute(query)
        return Response(
            status = HttpStatus.OK.value(),
            message = if (emailAlreadyExists) "이미 존재하는 이메일입니다." else "사용 가능한 이메일입니다.",
        )
    }

    @PostMapping("/existing/id")
    fun checkExistingServiceId(
        @RequestBody req: CheckExistingServiceIdReqDto,
    ): Response<Unit> {
        val serviceIdAlreadyExists =
            checkAlreadyExistingServiceIdUseCase.execute(CheckAlreadyExistingServiceIdUseCase.Query(req.serviceId))
        return Response(
            status = HttpStatus.OK.value(),
            message = if (serviceIdAlreadyExists) "이미 존재하는 아이디입니다." else "사용 가능한 아이디입니다.",
        )
    }

    @PostMapping("/verification")
    fun sendEmailVerification(
        @RequestBody req: VerifyEmailReqDto
    ): Response<Unit> {
        sendVerificationEmailUseCase.execute(SendVerificationEmailUseCase.Command(req.email))
        return Response(
            status = HttpStatus.OK.value(),
            message = "인증 이메일 전송에 성공했습니다."
        )
    }
}

