package com.zoin.rendezvous.domain.rendezvous.usecase

import com.zoin.rendezvous.domain.rendezvous.Rendezvous
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.domain.user.User
import com.zoin.rendezvous.domain.user.UserVO
import com.zoin.rendezvous.domain.user.repository.UserRepository
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.spyk
import io.mockk.verify
import kotlin.random.Random

class ReadRendezvousParticipantListUseCaseTest : DescribeSpec({
    lateinit var readRendezvousParticipantListUseCase: ReadRendezvousParticipantListUseCase
    lateinit var mockRendezvousRepository: RendezvousRepository
    lateinit var mockUserRepository: UserRepository

    beforeTest {
        mockRendezvousRepository = mockk()
        mockUserRepository = mockk()
        readRendezvousParticipantListUseCase = spyk(
            objToCopy = ReadRendezvousParticipantListUseCase(
                mockRendezvousRepository,
                mockUserRepository,
            )
        )
    }

    describe("Read Rendezvous Participant List UseCase") {
        context("under perfect circumstance") {
            var mockRendezvousId: Long = 0
            var mockReaderId: Long = 0
            lateinit var mockQuery: ReadRendezvousParticipantListUseCase.Query
            lateinit var mockRendezvous: Rendezvous
            lateinit var mockReader: User

            beforeTest {
                mockRendezvousId = Random.nextLong(1, 10)
                mockReaderId = Random.nextLong(1, 10)
                mockQuery = mockk {
                    every { rendezvousId } returns mockRendezvousId
                    every { readerId } returns mockReaderId
                    every { component1() } returns mockRendezvousId
                    every { component2() } returns mockReaderId
                }
                mockReader = mockk {
                    every { id } returns mockReaderId
                    every { mustGetId() } returns mockReaderId
                }
                mockRendezvous = mockk {
                    every { creator } returns mockReader
                    every { participants } returns arrayListOf(
                        mockk {
                            every { participant } returns mockReader
                        }
                    )
                }
                every {
                    mockRendezvousRepository.findByIdExcludeDeleted(mockRendezvousId)
                } returns mockRendezvous
                every {
                    mockUserRepository.findByIdExcludeDeleted(mockReaderId)
                } returns mockReader

                mockkObject(UserVO.Companion)
                every {
                    UserVO.of(mockReader)
                } returns mockk()
            }
            it("be executed properly.") {
                readRendezvousParticipantListUseCase.execute(mockQuery)

                verify(exactly = 1) {
                    mockRendezvousRepository.findByIdExcludeDeleted(mockRendezvousId)
                }
                verify(exactly = 1) {
                    mockUserRepository.findByIdExcludeDeleted(mockReaderId)
                }
            }
        }
    }
})
