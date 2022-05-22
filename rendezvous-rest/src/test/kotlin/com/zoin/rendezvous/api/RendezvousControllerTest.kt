package com.zoin.rendezvous.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.zoin.rendezvous.api.`interface`.dto.SaveRendezvousReqDto
import com.zoin.rendezvous.config.mock.MockConfiguration
import com.zoin.rendezvous.domain.rendezvous.usecase.CreateRendezvousUseCase
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import kotlin.random.Random

@Import(
    MockConfiguration::class
)
@WebMvcTest(RendezvousController::class)
@AutoConfigureRestDocs
class RendezvousControllerTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
) : DescribeSpec() {

    override fun extensions() = listOf(SpringExtension)

    @MockkBean(relaxed = true)
    private lateinit var mockCreateRendezvousUseCase: CreateRendezvousUseCase

    init {
        this.isolationMode = IsolationMode.InstancePerLeaf

        this.describe("Rendezvous controller는") {
            val mockCreateRendezvousReqDto = SaveRendezvousReqDto(
                title = "mockTitle",
                appointmentTime = LocalDateTime.now(),
                location = "mockLocation",
                requiredParticipantsCount = Random.nextInt(100),
                description = "mockDescription",
            )
            context("정상적인 post 요청에") {
                it("성공 응답한다") {
                    mockMvc.perform(
                        post("/api/v1/rendezvous")
                            .header("Authorization", "mock json web token")
                            .content(objectMapper.writeValueAsString(mockCreateRendezvousReqDto))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                        .andExpect(status().isOk)
                        .andDo(
                            document("rendezvous/post-rendezvous")
                        )
                }
            }
        }
    }
}
