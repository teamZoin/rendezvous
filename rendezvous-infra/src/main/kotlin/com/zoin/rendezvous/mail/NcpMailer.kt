package com.zoin.rendezvous.mail

import com.fasterxml.jackson.databind.ObjectMapper
import com.zoin.rendezvous.InfraEnvHolder
import com.zoin.rendezvous.infra.MailService
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.create
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.inject.Named

class NcpMailer(
    private val objectMapper: ObjectMapper,
    private val infraEnvHolder: InfraEnvHolder,
    private val customOkHttpClient: OkHttpClient,
) : MailService {
    private val mailApiClient: MailApiRetrofitClient = Retrofit.Builder()
        .baseUrl("https://mail.apigw.ntruss.com/")
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
        .client(customOkHttpClient)
        .build()
        .create()

    override fun sendVerificationEmail(targetEmail: String, code: String) {
        val httpMethod = "POST"
        val url = "/api/v1/mails"
        val currentTime: Long = System.currentTimeMillis()
        val secretKey = infraEnvHolder.ncpConfig.secretKey
        val signingKey = SecretKeySpec(secretKey.toByteArray(), "HmacSHA256")
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(signingKey)

        val message = "$httpMethod $url\n$currentTime\n" + infraEnvHolder.ncpConfig.accessId
        val rawHmac = mac.doFinal(message.toByteArray())
        val encodeBase64String = Base64.getEncoder().encodeToString(rawHmac)

        runBlocking {
            mailApiClient.sendVerificationCode(
                currentTime = currentTime,
                accessKey = infraEnvHolder.ncpConfig.accessId,
                encryptedSignature = encodeBase64String,
                req = SendMail.ReqDto(
                    templateId = SendMail.TemplateId.EMAIL_VERIFICATION.id,
                    recipients = listOf(
                        SendMail.Recipient(
                            address = targetEmail,
                            parameters = EmailVerification(
                                verificationCode = code,
                            )
                        )
                    )
                )
            )
        }
    }
}

class MockMailer : MailService {
    override fun sendVerificationEmail(targetEmail: String, code: String) {
        // empty body: 가짜 메일러
    }
}
