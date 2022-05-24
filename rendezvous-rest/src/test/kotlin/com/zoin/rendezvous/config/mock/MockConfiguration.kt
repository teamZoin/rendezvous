package com.zoin.rendezvous.config.mock

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class MockConfiguration {
    @Bean
    fun authTokenUtil() = MockAuthTokenUtil()
}
