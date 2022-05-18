package com.zoin.rendezvous.config

import com.zoin.rendezvous.UtilEnvHolder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class UtilModEnvConfig(
    private val jwtProperties: JwtProperties,
) {
    @Bean
    fun utilEnvHolder(
        env: Environment,
    ): UtilEnvHolder {
        val utilEnvHolder = UtilEnvHolder(env.activeProfiles)
        utilEnvHolder.jwtSecretKey = jwtProperties.secretKey
        return utilEnvHolder
    }
}
