package com.zoin.rendezvous.config

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Configuration
class ObjectMapperConfig {
    @Bean
    @Primary
    fun objectMapper(): ObjectMapper = ObjectMapper()
        .registerKotlinModule()
        .registerModule(
            JavaTimeModule().apply {
                val dateTimeFormat = DateTimeFormatter.ISO_LOCAL_DATE_TIME
                this.addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(dateTimeFormat))
                this.addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(dateTimeFormat))
            }
        )
        .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
}
