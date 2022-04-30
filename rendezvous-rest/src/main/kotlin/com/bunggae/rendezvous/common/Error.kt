package com.bunggae.rendezvous.common

import org.springframework.http.HttpStatus

enum class Error(var desc: String, val status: HttpStatus) {
    UNKNOWN_ERROR("unknown error", HttpStatus.INTERNAL_SERVER_ERROR)
}