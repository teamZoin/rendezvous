package com.zoin.rendezvous

class InfraEnvHolder(
    private val activeProfiles: Array<String>,

) {
    lateinit var emailConfig: EmailConfig
    lateinit var jwtSecretKey: String
    lateinit var ncpConfig: NCPConfig

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

    fun setEmailConfig(username: String, password: String) {
        emailConfig = EmailConfig(username, password)
    }

    fun setNcpConfig(accessId: String, secretKey: String) {
        ncpConfig = NCPConfig(
            accessId = accessId,
            secretKey = secretKey,
        )
    }
}

data class EmailConfig(
    val address: String,
    val password: String,
)

data class NCPConfig(
    val accessId: String,
    val secretKey: String,
)
