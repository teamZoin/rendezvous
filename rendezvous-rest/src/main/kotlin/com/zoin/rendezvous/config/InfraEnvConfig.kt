package com.zoin.rendezvous.config

import com.zoin.rendezvous.InfraEnvHolder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class InfraEnvConfig(
    private val emailCredentialProperties: EmailCredentialProperties,
) {
    @Bean
    fun infraEnvHolder(
        env: Environment,
    ): InfraEnvHolder {
        val infraEnvHolder = InfraEnvHolder(env.activeProfiles)
        val (address, password) = emailCredentialProperties
        infraEnvHolder.setEmailConfig(address, password)
        return infraEnvHolder
    }
}
