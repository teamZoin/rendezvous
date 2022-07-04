package com.zoin.rendezvous.jwt

import com.zoin.rendezvous.UtilEnvHolder
import com.zoin.rendezvous.util.authToken.TokenPayload
import io.jsonwebtoken.Jwts
import java.time.Duration
import java.util.Date

class MockJwtUtil(
    private val utilEnvHolder: UtilEnvHolder,
) : JwtUtil(utilEnvHolder) {
    override fun generateToken(payload: TokenPayload): String {
        val now = Date()
        val userId = payload.userId
        return Jwts.builder()
            .claim("userId", userId)
            .setExpiration(Date(now.time + Duration.ofDays(365).toMillis()))
            .signWith(secretKey, signatureAlgorithm)
            .compact()
    }
}
