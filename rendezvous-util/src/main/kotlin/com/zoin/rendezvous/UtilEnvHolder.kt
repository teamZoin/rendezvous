package com.zoin.rendezvous

class UtilEnvHolder(
    private val activeProfiles: Array<String>,
) {
    lateinit var jwtSecretKey: String

    companion object {
        const val PROFILE_LOCAL = "local"
        const val PROFILE_PROD = "prod"
    }

    fun isLocal(): Boolean {
        return activeProfiles.contains(PROFILE_LOCAL)
    }

    fun isProd(): Boolean {
        return activeProfiles.contains(PROFILE_PROD)
    }
}
