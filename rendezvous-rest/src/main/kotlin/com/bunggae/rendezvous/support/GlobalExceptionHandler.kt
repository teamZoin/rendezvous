package com.bunggae.rendezvous.support

import com.bunggae.rendezvous.common.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {
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
    fun runtimeException(e: RuntimeException): ResponseEntity<Response<String>> =
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Response(LocalDateTime.now(), 500, e.message))
}