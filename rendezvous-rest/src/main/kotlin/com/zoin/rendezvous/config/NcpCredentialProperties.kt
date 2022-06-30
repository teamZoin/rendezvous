package com.zoin.rendezvous.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "ncp")
data class NcpCredentialProperties(
    val accessKeyId: String,
    val secretKey: String,
)
