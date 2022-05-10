package com.bunggae.rendezvous

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class RendezvousRestApplication

fun main(vararg args: String) {
    runApplication<RendezvousRestApplication>(*args)
}