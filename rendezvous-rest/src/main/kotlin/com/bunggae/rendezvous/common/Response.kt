package com.bunggae.rendezvous.common

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
class Response<T>(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int = 0,
    val message: String? = null,
    val data: T? = null,
)