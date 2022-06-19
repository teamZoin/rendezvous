package com.zoin.rendezvous.domain.rendezvous.usecase

import com.zoin.rendezvous.domain.rendezvous.Rendezvous
import com.zoin.rendezvous.domain.rendezvous.RendezvousParticipant
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousParticipantRepository
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.domain.user.User
import com.zoin.rendezvous.domain.user.repository.UserRepository
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlin.random.Random

class CreateRendezvousUseCaseTest : DescribeSpec({
    lateinit var createRendezvousUseCase: CreateRendezvousUseCase
    lateinit var mockUserRepository: UserRepository
    lateinit var mockRendezvousRepository: RendezvousRepository
    lateinit var mockRendezvousParticipantRepository: RendezvousParticipantRepository
    lateinit var mockUser: User
    lateinit var mockRendezvous: Rendezvous
    lateinit var mockCommand: CreateRendezvousUseCase.Command
    lateinit var mockRendezvousParticipant: RendezvousParticipant
    var mockUserId: Long = 0

    beforeTest {
        mockUserId = Random.nextLong(1, 10)
        mockCommand = mockk(relaxed = true) {
            every { component1() } returns mockUserId
            every { creatorId } returns mockUserId
        }
        mockUser = mockk(relaxed = true) {
            every { id } returns mockUserId
        }
        mockRendezvous = mockk(relaxed = true) {
            every { creator } returns mockUser
        }

        mockRendezvousParticipant = mockk {
            every { rendezvous } returns mockRendezvous
            every { participant } returns mockUser
        }

        mockUserRepository = mockk {
            every {
                findByIdExcludeDeleted(mockUserId)
            } returns mockUser
        }
        mockRendezvousRepository = mockk {
            every {
                save(mockRendezvous)
            } returns mockRendezvous
        }
        mockRendezvousParticipantRepository = mockk {
            every {
                save(any())
            } returns mockRendezvousParticipant
        }
        createRendezvousUseCase = spyk(
            objToCopy = CreateRendezvousUseCase(
                mockUserRepository,
                mockRendezvousRepository,
                mockRendezvousParticipantRepository,
            )
        )
    }

    describe("Create rendezvous usecase") {

        context("under perfect circumstance") {

            it("be executed properly.") {
                createRendezvousUseCase.execute(mockCommand)
                verify(exactly = 1) {
                    mockRendezvousRepository.save(mockRendezvous)
                }
                verify(exactly = 1) {
                    mockRendezvousParticipantRepository.save(mockRendezvousParticipant)
                }
            }
        }
    }
})
