package com.bunggae.rendezvous.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "bunggae.email")
data class EmailCredentialProperties(
    val address: String,
    val password: String,
)
