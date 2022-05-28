package com.zoin.rendezvous.domain.rendezvous

import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.domain.user.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import java.time.LocalDateTime
import kotlin.random.Random

class RendezvousTest : DescribeSpec({
    lateinit var rendezvous: Rendezvous

    describe("beDeletedBy()") {
        lateinit var mockCreator: User
        lateinit var mockUser: User

        beforeTest {
            mockCreator = mockk()
            rendezvous = spyk(
                objToCopy = Rendezvous(
                    creator = mockCreator,
                    title = "",
                    appointmentTime = LocalDateTime.now(),
                    location = "",
                    requiredParticipantsCount = Random.nextInt(1, 10),
                )
            )
        }
        context("번개 작성자가 아닌 유저가 기능을 실행하면") {
            lateinit var mockRendezvousRepository: RendezvousRepository

            beforeTest {
                mockUser = mockk()
                mockRendezvousRepository = mockk {
                    every { save(any()) } returns mockk()
                }
            }
            it("예외를 던진다") {
                shouldThrow<IllegalAccessException> {
                    rendezvous.beClosedBy(
                        user = mockUser,
                        rendezvousRepository = mockRendezvousRepository
                    )
                }
            }
        }
    }
})
