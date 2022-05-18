package com.zoin.rendezvous.domain.user

import com.zoin.rendezvous.domain.rendezvous.Rendezvous
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.util.PasswordEncoder
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlin.random.Random

class UserTest : DescribeSpec({
    lateinit var mockRendezvousRepository: RendezvousRepository
    lateinit var mockPasswordEncoder: PasswordEncoder

    isolationMode = IsolationMode.InstancePerLeaf

    beforeTest {
        mockRendezvousRepository = mockk {
            justRun { delete(any()) }
        }
        mockPasswordEncoder = mockk {
            every {
                encode(any(), any())
            } answers { arg(0) }
        }
    }

    describe("유저가") {
        lateinit var mockUser: User
        lateinit var mockPassword: String
        lateinit var mockHashedPassword: ByteArray
        lateinit var mockSalt: ByteArray

        beforeTest {
            mockSalt = Random.nextBytes(10)
            mockPassword = "password"
            mockHashedPassword = mockPasswordEncoder.encode(mockSalt, mockPassword)
            mockUser = User(
                email = "test@gmail.com",
                salt = mockSalt,
                hashedPassword = mockHashedPassword,
                serviceId = "serviceId",
                userName = "name",
            )
        }

        context("자신이 생성한 번개를 삭제할 때") {
            lateinit var mockRendezvous: Rendezvous
            beforeTest {
                mockRendezvous = mockk()
            }
            it("성공적으로 삭제된다") {
                mockUser.deleteRendezvous(mockRendezvousRepository, mockRendezvous)
                verify {
                    mockRendezvousRepository.delete(mockRendezvous)
                }
            }
        }
    }
})
