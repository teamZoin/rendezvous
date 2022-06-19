package com.zoin.rendezvous.util.authToken

interface AuthTokenUtil {
    fun generateToken(payload: TokenPayload): String
    fun decodeToken(token: String): TokenPayload
}
