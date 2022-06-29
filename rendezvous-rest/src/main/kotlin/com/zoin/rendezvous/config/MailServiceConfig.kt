package com.zoin.rendezvous.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.zoin.rendezvous.InfraEnvHolder
import com.zoin.rendezvous.infra.MailService
import com.zoin.rendezvous.mail.MockMailer
import com.zoin.rendezvous.mail.NcpMailer
import okhttp3.OkHttpClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MailServiceConfig(
    private val objectMapper: ObjectMapper,
    private val infraEnvHolder: InfraEnvHolder,
    private val customOkHttpClient: OkHttpClient,
) {
    @Bean
    @ConditionalOnProperty(name = ["mail.mock"], havingValue = "true")
    fun mockMailService(): MailService = MockMailer()

    @Bean
    @ConditionalOnProperty(name = ["mail.mock"], havingValue = "false")
    fun realMailService(): MailService = NcpMailer(objectMapper, infraEnvHolder, customOkHttpClient)
}
