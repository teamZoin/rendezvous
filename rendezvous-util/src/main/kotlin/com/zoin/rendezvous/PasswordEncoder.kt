package com.zoin.rendezvous

import com.zoin.rendezvous.util.PasswordEncoder
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.inject.Named

@Named
class PasswordEncoder : PasswordEncoder {
    override fun generateSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return salt
    }

    override fun encode(salt: ByteArray, rawPassword: String): ByteArray {
        val keySpec = PBEKeySpec(rawPassword.toCharArray(), salt, 65536, 128)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        return secretKeyFactory.generateSecret(keySpec).encoded
    }
}
