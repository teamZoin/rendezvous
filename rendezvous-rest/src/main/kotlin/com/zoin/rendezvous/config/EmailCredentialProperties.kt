package com.zoin.rendezvous.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "zoin.email")
data class EmailCredentialProperties(
    val address: String,
    val password: String,
)
