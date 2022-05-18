package com.bunggae.rendezvous.support

import com.bunggae.rendezvous.api.`interface`.Response
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = KotlinLogging.logger { }

    @ExceptionHandler(value = [IllegalStateException::class, IllegalArgumentException::class])
    fun clientException(e: RuntimeException): ResponseEntity<Response<String>> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                Response(
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.BAD_REQUEST.value(),
                    message = e.toString()
                )
            )

    @ExceptionHandler(RuntimeException::class)
    fun runtimeException(e: RuntimeException): ResponseEntity<Response<String>> {
        logger.error { "$e occurs in ${e.stackTrace[0]}" }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Response(LocalDateTime.now(), 500, e.message))
    }
}
