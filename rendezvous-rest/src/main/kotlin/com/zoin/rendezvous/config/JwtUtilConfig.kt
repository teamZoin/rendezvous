package com.zoin.rendezvous.config

import com.zoin.rendezvous.UtilEnvHolder
import com.zoin.rendezvous.jwt.JwtUtil
import com.zoin.rendezvous.jwt.MockJwtUtil
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtUtilConfig(
    private val utilEnvHolder: UtilEnvHolder,
) {
    @Bean
    @ConditionalOnProperty(name = ["jwt.mock"], havingValue = "false")
    fun jwtUtil() = JwtUtil(utilEnvHolder)

    @Bean
    @ConditionalOnProperty(name = ["jwt.mock"], havingValue = "true")
    fun mockJwtUtil() = MockJwtUtil(utilEnvHolder)
}
