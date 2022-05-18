package com.bunggae.rendezvous.api.user

import com.bunggae.rendezvous.api.`interface`.Response
import com.bunggae.rendezvous.api.`interface`.dto.CheckExistingServiceIdReqDto
import com.bunggae.rendezvous.api.`interface`.dto.CheckExitingEmailReqDto
import com.bunggae.rendezvous.api.`interface`.dto.UpdateUserProfileImageReqDto
import com.bunggae.rendezvous.api.`interface`.dto.UpdateUserProfileImageResDto
import com.bunggae.rendezvous.api.`interface`.dto.UserLogInReqDto
import com.bunggae.rendezvous.api.`interface`.dto.UserSignUpReqDto
import com.bunggae.rendezvous.api.`interface`.dto.VerifyEmailReqDto
import com.bunggae.rendezvous.resolver.AuthTokenPayload
import com.bunggae.rendezvous.user.application.usecase.CheckAlreadyExistingEmailUseCase
import com.bunggae.rendezvous.user.application.usecase.CheckAlreadyExistingServiceIdUseCase
import com.bunggae.rendezvous.user.application.usecase.CreateUserUseCase
import com.bunggae.rendezvous.user.application.usecase.LoginUseCase
import com.bunggae.rendezvous.user.application.usecase.SendVerificationEmailUseCase
import com.bunggae.rendezvous.user.application.usecase.UpdateUserProfileImageUseCase
import com.bunggae.rendezvous.util.authToken.AuthTokenUtil
import com.bunggae.rendezvous.util.authToken.TokenPayload
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class Controller(
    private val createUserUseCase: CreateUserUseCase,
    private val loginUseCase: LoginUseCase,
    private val updateUserProfileImageUseCase: UpdateUserProfileImageUseCase,
    private val checkAlreadyExistingEmailUseCase: CheckAlreadyExistingEmailUseCase,
    private val checkAlreadyExistingServiceIdUseCase: CheckAlreadyExistingServiceIdUseCase,
    private val sendVerificationEmailUseCase: SendVerificationEmailUseCase,
    private val authTokenUtil: AuthTokenUtil,
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

        val userId = loginUseCase.execute(
            LoginUseCase.Query(
                email = email,
                password = password,
            )
        )

        val authToken = authTokenUtil.generateToken(
            TokenPayload(
                userId = userId
            )
        )

        return Response(
            status = HttpStatus.OK.value(),
            message = "로그인 성공",
            data = authToken,
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

    @PutMapping("/profile-image")
    fun updateProfileImage(
        @AuthTokenPayload payload: TokenPayload,
        @RequestBody req: UpdateUserProfileImageReqDto,
    ): Response<UpdateUserProfileImageResDto> {

        val (userId) = payload
        val user =
            updateUserProfileImageUseCase.execute(UpdateUserProfileImageUseCase.Command(userId, req.profileImgUrl))

        return Response(
            status = HttpStatus.OK.value(),
            message = "프로필 이미지 업데이트에 성공했습니다.",
            data = UpdateUserProfileImageResDto(
                userId = user.id!!,
                profileImgUrl = user.profileImgUrl ?: "",
            )
        )
    }
}
