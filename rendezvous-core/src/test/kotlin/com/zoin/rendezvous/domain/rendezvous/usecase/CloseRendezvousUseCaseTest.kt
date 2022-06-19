package com.zoin.rendezvous.domain.rendezvous.usecase

import com.zoin.rendezvous.domain.rendezvous.Rendezvous
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.domain.user.User
import com.zoin.rendezvous.domain.user.repository.UserRepository
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlin.random.Random

class CloseRendezvousUseCaseTest : DescribeSpec({
    lateinit var closeRendezvousUseCase: CloseRendezvousUseCase
    lateinit var userRepository: UserRepository
    lateinit var rendezvousRepository: RendezvousRepository

    lateinit var mockCommand: CloseRendezvousUseCase.Command
    var mockUserId: Long = 0
    var mockRendezvousId: Long = 0
    lateinit var mockUser: User
    lateinit var mockRendezvous: Rendezvous

    beforeTest {
        userRepository = mockk()
        rendezvousRepository = mockk()
        closeRendezvousUseCase = spyk(
            objToCopy = CloseRendezvousUseCase(
                userRepository, rendezvousRepository,
            )
        )
    }

    describe("Close rendezvous usecase") {
        context("under perfect circumstance") {
            beforeTest {
                mockUserId = Random.nextLong(1, 10)
                mockRendezvousId = Random.nextLong(1, 10)
                mockCommand = CloseRendezvousUseCase.Command(
                    userId = mockUserId,
                    rendezvousId = mockRendezvousId,
                )
                mockUser = mockk(relaxed = true)
                mockRendezvous = mockk {
                    every { creator } returns mockUser

                    // 도메인 모델 메서드는 도메인 모델의 책임이므로 모킹한다.
                    justRun { beClosedBy(rendezvousRepository, mockUser) }
                }
                every {
                    userRepository.findByIdExcludeDeleted(mockUserId)
                } returns mockUser

                every {
                    rendezvousRepository.findByIdExcludeDeleted(mockRendezvousId)
                } returns mockRendezvous
            }
            it("should be executed properly.") {
                closeRendezvousUseCase.execute(mockCommand)

                verify(exactly = 1) {
                    mockRendezvous.beClosedBy(rendezvousRepository, mockUser)
                }
            }
        }
    }
})
