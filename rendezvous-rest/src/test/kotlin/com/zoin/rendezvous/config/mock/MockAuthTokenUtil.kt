package com.zoin.rendezvous.config.mock

import com.zoin.rendezvous.util.authToken.AuthTokenUtil
import com.zoin.rendezvous.util.authToken.TokenPayload

class MockAuthTokenUtil : AuthTokenUtil {
    override fun generateToken(payload: TokenPayload): String {
        return "mock return value"
    }

    override fun decodeToken(token: String): TokenPayload {
        return TokenPayload(
            userId = 1
        )
    }
}
