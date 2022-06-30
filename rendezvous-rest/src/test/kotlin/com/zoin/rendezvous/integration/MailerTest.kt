package com.zoin.rendezvous.integration

import com.zoin.rendezvous.domain.user.QuitReason
import com.zoin.rendezvous.domain.user.User
import com.zoin.rendezvous.domain.user.UserQuitLog
import com.zoin.rendezvous.infra.MailService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.IfProfileValue
import kotlin.random.Random

@SpringBootTest
class MailerTest constructor(
    @Autowired
    private val mailService: MailService
) {
    @Disabled("기능 개발을 위한 테스트")
    @Test
    fun `회원 탙퇴 메일 전송`() {
        val randomId = Random.nextLong(1, 10)
        val mockUser = mockk<User> {
            every { mustGetId() } returns randomId
            every { userName } returns "mockUserName"
        }
        mailService.sendQuitLog(
            UserQuitLog(
                user = mockUser,
                reason = QuitReason.NOTHING_TO_GATHER,
                etcDescription = null,
            )
        )
    }
}
