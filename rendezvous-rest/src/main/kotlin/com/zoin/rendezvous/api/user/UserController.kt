package com.zoin.rendezvous.api.user

import com.zoin.rendezvous.api.common.Response
import com.zoin.rendezvous.api.user.dto.CheckExistingServiceIdReqDto
import com.zoin.rendezvous.api.user.dto.CheckExitingEmailReqDto
import com.zoin.rendezvous.api.user.dto.LoginResDto
import com.zoin.rendezvous.api.user.dto.ReadRendezvousListCreatedByUserReqDto
import com.zoin.rendezvous.api.user.dto.SearchUserByServiceIdReqDto
import com.zoin.rendezvous.api.user.dto.SetUserNotificationReqDto
import com.zoin.rendezvous.api.user.dto.UpdatePasswordReqDto
import com.zoin.rendezvous.api.user.dto.UpdateUserProfileImageReqDto
import com.zoin.rendezvous.api.user.dto.UpdateUserProfileImageResDto
import com.zoin.rendezvous.api.user.dto.UpdateUserProfileReqDto
import com.zoin.rendezvous.api.user.dto.UpdateUserProfileResDto
import com.zoin.rendezvous.api.user.dto.UserAndRelationship
import com.zoin.rendezvous.api.user.dto.UserLogInReqDto
import com.zoin.rendezvous.api.user.dto.UserSignUpReqDto
import com.zoin.rendezvous.api.user.dto.VerifyEmailReqDto
import com.zoin.rendezvous.domain.emailAuth.usecase.SendVerificationEmailUseCase
import com.zoin.rendezvous.domain.rendezvous.RendezvousVO
import com.zoin.rendezvous.domain.rendezvous.usecase.ReadRendezvousCreatedByUserUseCase
import com.zoin.rendezvous.domain.user.UserVO
import com.zoin.rendezvous.domain.user.usecase.CheckAlreadyExistingEmailUseCase
import com.zoin.rendezvous.domain.user.usecase.CheckAlreadyExistingServiceIdUseCase
import com.zoin.rendezvous.domain.user.usecase.CheckIfInputMatchesUserPasswordUseCase
import com.zoin.rendezvous.domain.user.usecase.CreateUserUseCase
import com.zoin.rendezvous.domain.user.usecase.LoginUseCase
import com.zoin.rendezvous.domain.user.usecase.SearchUserByServiceIdUseCase
import com.zoin.rendezvous.domain.user.usecase.UpdatePasswordUseCase
import com.zoin.rendezvous.domain.user.usecase.UpdateUserNotificationUseCase
import com.zoin.rendezvous.domain.user.usecase.UpdateUserProfileImageUseCase
import com.zoin.rendezvous.domain.user.usecase.UpdateUserProfileUseCase
import com.zoin.rendezvous.resolver.AuthTokenPayload
import com.zoin.rendezvous.util.authToken.AuthTokenUtil
import com.zoin.rendezvous.util.authToken.TokenPayload
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val createUserUseCase: CreateUserUseCase,
    private val loginUseCase: LoginUseCase,
    private val updateUserProfileImageUseCase: UpdateUserProfileImageUseCase,
    private val checkAlreadyExistingEmailUseCase: CheckAlreadyExistingEmailUseCase,
    private val checkAlreadyExistingServiceIdUseCase: CheckAlreadyExistingServiceIdUseCase,
    private val sendVerificationEmailUseCase: SendVerificationEmailUseCase,
    private val updateUserNotificationUseCase: UpdateUserNotificationUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val checkIfInputMatchesUserPasswordUseCase: CheckIfInputMatchesUserPasswordUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val searchUserByServiceIdUseCase: SearchUserByServiceIdUseCase,
    private val readRendezvousCreatedByUserUseCase: ReadRendezvousCreatedByUserUseCase,
    private val authTokenUtil: AuthTokenUtil,
) {
    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody req: UserSignUpReqDto,
    ): Response<String> {
        val (email, password, userName, serviceId, profileImgUrl) = req
        val createdUser = createUserUseCase.execute(
            CreateUserUseCase.Command(
                email = email,
                password = password,
                userName = userName,
                serviceId = serviceId,
                profileImgUrl = profileImgUrl,
            )
        )

        val authToken = authTokenUtil.generateToken(
            TokenPayload(
                userId = createdUser.mustGetId()
            )
        )

        return Response(
            status = HttpStatus.OK.value(),
            message = "회원가입 성공",
            data = authToken,
        )
    }

    @PostMapping("/log-in")
    fun logIn(
        @RequestBody req: UserLogInReqDto,
    ): Response<LoginResDto> {
        val (email, password) = req

        val user = loginUseCase.execute(
            LoginUseCase.Query(
                email = email,
                password = password,
            )
        )

        val authToken = authTokenUtil.generateToken(
            TokenPayload(
                userId = user.mustGetId()
            )
        )

        return Response(
            status = HttpStatus.OK.value(),
            message = "로그인 성공",
            data = LoginResDto(
                UserVO.of(user),
                authToken
            ),
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

    @PutMapping("")
    fun updateProfile(
        @AuthTokenPayload payload: TokenPayload,
        @RequestBody req: UpdateUserProfileReqDto,
    ): Response<UpdateUserProfileResDto> {
        val updatedUser = updateUserProfileUseCase.execute(
            UpdateUserProfileUseCase.Command(
                userId = payload.userId,
                newUserName = req.newUserName,
                newProfileImgUrl = req.newProfileImgUrl,
            )
        )
        return Response(
            status = HttpStatus.OK.value(),
            data = UpdateUserProfileResDto(
                updatedUserName = updatedUser.userName,
                updatedProfileImgUrl = updatedUser.profileImgUrl,
            )
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

    @PutMapping("/notification")
    fun setUserNotificationOnOrOff(
        @AuthTokenPayload payload: TokenPayload,
        @RequestBody setUserNotificationReqDto: SetUserNotificationReqDto,
    ) {
        val (on) = setUserNotificationReqDto
        val (userId) = payload
        updateUserNotificationUseCase.execute(UpdateUserNotificationUseCase.Command(userId, on))
    }

    @PutMapping("/password")
    fun updatePassword(
        @AuthTokenPayload payload: TokenPayload,
        @RequestBody req: UpdatePasswordReqDto,
    ): Response<Any> {
        val (doesMatch, user) = checkIfInputMatchesUserPasswordUseCase.execute(
            CheckIfInputMatchesUserPasswordUseCase.Query(
                userId = payload.userId,
                input = req.password,
            )
        )

        val message = if (doesMatch) {
            updatePasswordUseCase.execute(
                UpdatePasswordUseCase.Command(
                    user,
                    req.newPassword,
                )
            )
            "비밀번호가 변경되었습니다."
        } else "비밀번호가 일치하지 않습니다."

        return Response(
            status = HttpStatus.OK.value(),
            message = message
        )
    }

    @DeleteMapping
    fun deleteUser(
        @AuthTokenPayload payload: TokenPayload,
    ) {
        // TODO
    }

    @GetMapping
    fun searchUserByServiceId(
        @AuthTokenPayload payload: TokenPayload,
        @RequestBody req: SearchUserByServiceIdReqDto,
    ): Response<List<UserAndRelationship>> {
        val userList = searchUserByServiceIdUseCase.execute(
            SearchUserByServiceIdUseCase.Query(
                userId = payload.userId,
                searchIdInput = req.searchInput,
            )
        ).map { (user, relationship) ->
            UserAndRelationship(
                user = UserVO.of(user),
                relationshipOrder = relationship.ordinal,
            )
        }
        return Response(
            message = "유저 검색 성공",
            data = userList
        )
    }

    @GetMapping("/rendezvous")
    fun readRendezvousListCreatedByUser(
        @AuthTokenPayload tokenPayload: TokenPayload,
        @RequestParam(value = "size") size: Long,
        @RequestParam(value = "cursor") cursorId: Long? = null,
        @RequestBody req: ReadRendezvousListCreatedByUserReqDto,
    ): Response<List<RendezvousVO>> {
        val rendezvousList = readRendezvousCreatedByUserUseCase.execute(
            ReadRendezvousCreatedByUserUseCase.Query(
                userId = tokenPayload.userId,
                isClosed = req.isClosed,
                size = size,
                cursorId = cursorId,
            )
        )
        return Response(
            message = "내가 작성한 번개 리스트 조회 성공",
            data = rendezvousList.map { rendezvous ->
                RendezvousVO.of(rendezvous)
            }
        )
    }
}
