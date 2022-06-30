package com.zoin.rendezvous.mail

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MailApiRetrofitClient {
    @POST("api/v1/mails")
    suspend fun sendVerificationCode(
        @Header("x-ncp-apigw-timestamp") currentTime: Long,
        @Header("x-ncp-iam-access-key") accessKey: String,
        @Header("x-ncp-apigw-signature-v2") encryptedSignature: String,
        @Header("x-ncp-lang") language: String? = "ko-KR",
        @Body req: SendMail.ReqDto<EmailVerification>,
    ): SendMail.ResDto
}

class SendMail {
    enum class TemplateId(val id: Long) {
        EMAIL_VERIFICATION(6559),
        RENDEZVOUS_REPORT(6552),
        DELETED_USER(6551),
    }

    data class ReqDto<T>(
        @JsonProperty("templateSid")
        val templateId: Long,
        val recipients: List<Recipient<EmailVerification>>,
    )

    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class Recipient<T>(
        val address: String,
        val name: String? = null,
        val type: String = "R",
        val parameters: T,
    )

    data class ResDto(
        @JsonProperty("requestId")
        val requestId: String?,
        val count: Long?,
    )
}

data class EmailVerification(
    @JsonProperty("verification_code")
    val verificationCode: String,
)
