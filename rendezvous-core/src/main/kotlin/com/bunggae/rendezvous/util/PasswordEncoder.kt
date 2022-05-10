package com.bunggae.rendezvous.util

interface PasswordEncoder {
    fun generateSalt(): ByteArray
    fun encode(salt: ByteArray, rawPassword: String): ByteArray
}
