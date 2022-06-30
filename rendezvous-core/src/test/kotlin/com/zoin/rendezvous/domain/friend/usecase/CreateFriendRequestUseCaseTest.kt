package com.zoin.rendezvous.domain.friend.usecase

import com.zoin.rendezvous.domain.friend.Friend
import com.zoin.rendezvous.domain.friend.Status
import com.zoin.rendezvous.domain.friend.repository.FriendRepository
import com.zoin.rendezvous.domain.user.User
import com.zoin.rendezvous.domain.user.repository.UserRepository
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.random.Random

class CreateFriendRequestUseCaseTest : DescribeSpec({
    lateinit var useCase: CreateFriendRequestUseCase
    lateinit var mockUserRepository: UserRepository
    lateinit var mockFriendRepository: FriendRepository

    beforeTest {
        mockUserRepository = mockk()
        mockFriendRepository = mockk()
        useCase = spyk(
            objToCopy = CreateFriendRequestUseCase(
                mockUserRepository,
                mockFriendRepository,
            )
        )
    }

    describe("친구 신청 기능은") {
        var requesterId: Long = 0
        var targetUserId: Long = 0
        lateinit var requester: User
        lateinit var targetUser: User
        beforeTest {
            requesterId = Random.nextLong(10, 100)
            targetUserId = Random.nextLong(10, 100)
            requester = mockk()
            targetUser = mockk()
            every { mockUserRepository.findByIdExcludeDeleted(requesterId) } returns requester
            every { mockUserRepository.findByIdExcludeDeleted(targetUserId) } returns targetUser
        }
        context("유저가 친구가 아닌 유저에게 사용할 때") {
            beforeTest {
                every {
                    mockFriendRepository.ifUsersAreAlreadyFriend(requester, targetUser)
                } returns false
                every {
                    mockFriendRepository.findByUserAndFriend(requester, targetUser)
                } returns null
                every {
                    mockFriendRepository.findByUserAndFriend(targetUser, requester)
                } returns null
                every { mockFriendRepository.save(any()) } returns mockk()
            }
            it("성공한다.") {
                assertDoesNotThrow {
                    useCase.execute(
                        CreateFriendRequestUseCase.Command(
                            userId = requesterId,
                            targetUserId = targetUserId,
                        )
                    )
                }
            }
        }

        context("유저가 이미 친구인 유저에게 사용할 때") {
            beforeTest {
                every { mockFriendRepository.ifUsersAreAlreadyFriend(requester, targetUser) } returns true
            }
            it("예외를 던진다") {
                assertThrows<IllegalStateException> {
                    useCase.execute(
                        CreateFriendRequestUseCase.Command(
                            userId = requesterId,
                            targetUserId = targetUserId,
                        )
                    )
                }
            }
        }

        context("유저가 이미 친구신청을 했을 때") {
            lateinit var mockFriendRelationship: Friend
            beforeTest {
                every { mockFriendRepository.ifUsersAreAlreadyFriend(requester, targetUser) } returns false
                mockFriendRelationship = mockk {
                    every { user } returns requester
                    every { friend } returns targetUser
                    every { status } returns Status.PENDING
                }
                every {
                    mockFriendRepository.findByUserAndFriend(requester, targetUser)
                } returns mockFriendRelationship
            }
            it("예외를 던진다") {
                assertThrows<IllegalStateException> {
                    useCase.execute(
                        CreateFriendRequestUseCase.Command(
                            userId = requesterId,
                            targetUserId = targetUserId,
                        )
                    )
                }
            }
        }
        context("유저가 이미 친구신청을 받았을 때") {
            lateinit var mockFriendRelationship: Friend
            beforeTest {
                every { mockFriendRepository.ifUsersAreAlreadyFriend(requester, targetUser) } returns false
                mockFriendRelationship = mockk {
                    every { user } returns targetUser
                    every { friend } returns requester
                    every { status } returns Status.PENDING
                }
                every {
                    mockFriendRepository.findByUserAndFriend(requester, targetUser)
                } returns null
                every {
                    mockFriendRepository.findByUserAndFriend(targetUser, requester)
                } returns mockFriendRelationship
            }
            it("예외를 던진다") {
                assertThrows<IllegalStateException> {
                    useCase.execute(
                        CreateFriendRequestUseCase.Command(
                            userId = requesterId,
                            targetUserId = targetUserId,
                        )
                    )
                }
            }
        }
    }
})
