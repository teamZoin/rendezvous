package com.zoin.rendezvous.config

import com.zoin.rendezvous.InfraEnvHolder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class InfraEnvConfig(
    private val emailCredentialProperties: EmailCredentialProperties,
    private val jwtProperties: JwtProperties,
    private val ncpCredentialProperties: NcpCredentialProperties,
) {
    @Bean
    fun infraEnvHolder(
        env: Environment,
    ): InfraEnvHolder {
        val infraEnvHolder = InfraEnvHolder(env.activeProfiles)
        val (address, password) = emailCredentialProperties

        infraEnvHolder.setEmailConfig(address, password)

        infraEnvHolder.jwtSecretKey = jwtProperties.secretKey

        infraEnvHolder.setNcpConfig(ncpCredentialProperties.accessKeyId, ncpCredentialProperties.secretKey)

        return infraEnvHolder
    }
}
