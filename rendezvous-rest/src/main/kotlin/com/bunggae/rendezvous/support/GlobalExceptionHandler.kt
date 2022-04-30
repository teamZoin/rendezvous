package com.bunggae.rendezvous.support

import com.bunggae.rendezvous.common.Exception
import com.bunggae.rendezvous.common.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun applicationException(e: Exception): ResponseEntity<Response<String>> =
        ResponseEntity
            .status(e.error.status)
            .body(Response(LocalDateTime.now(), e.error.status.value(), e.message))

    @ExceptionHandler(RuntimeException::class)
    fun runtimeException(e: RuntimeException): ResponseEntity<Response<String>> =
        ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Response(LocalDateTime.now(), 500, e.message))
}