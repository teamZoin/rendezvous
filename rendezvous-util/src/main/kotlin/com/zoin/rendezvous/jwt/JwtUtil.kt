package com.zoin.rendezvous.jwt

import com.fasterxml.jackson.core.JsonProcessingException
import com.zoin.rendezvous.UtilEnvHolder
import com.zoin.rendezvous.util.authToken.AuthTokenUtil
import com.zoin.rendezvous.util.authToken.TokenPayload
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.security.Key
import java.time.Duration
import java.util.Base64
import java.util.Date
import javax.crypto.spec.SecretKeySpec
import javax.inject.Named

@Named
class JwtUtil(
    private val utilEnvHolder: UtilEnvHolder,
) : AuthTokenUtil {
    private val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS256
    private val secretKey: Key =
        SecretKeySpec(
            Base64.getEncoder().encode(utilEnvHolder.jwtSecretKey.toByteArray()),
            signatureAlgorithm.jcaName,
        )

    @Throws(JsonProcessingException::class)
    override fun generateToken(payload: TokenPayload): String {
        val now = Date()
        val userId = payload.userId
        return Jwts.builder()
            .claim("userId", userId)
            .setExpiration(Date(now.time + Duration.ofDays(1).toMillis()))
            .signWith(secretKey, signatureAlgorithm)
            .compact()
    }

    @Throws(JsonProcessingException::class)
    override fun decodeToken(token: String): TokenPayload {
        val jwtParser = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()

        val claims: Claims = jwtParser
            .parseClaimsJws(token)
            .body

        val userId = claims.get("userId", Integer::class.java).toLong()
        return TokenPayload(
            userId,
        )
    }
}
