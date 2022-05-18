package com.bunggae.rendezvous.mail

import com.bunggae.rendezvous.InfraEnvHolder
import com.bunggae.rendezvous.user.application.infra.MailService
import jakarta.mail.Authenticator
import jakarta.mail.Message
import jakarta.mail.MessagingException
import jakarta.mail.PasswordAuthentication
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.AddressException
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import java.util.Properties
import javax.inject.Named

@Named
class GmailService(
    private val infraEnvHolder: InfraEnvHolder,
) : MailService {
    private val address: String = infraEnvHolder.emailConfig.address
    private val password: String = infraEnvHolder.emailConfig.password

    private val props = Properties().apply {
        this["mail.smtp.auth"] = "true"
        this["mail.smtp.host"] = "smtp.gmail.com"
        this["mail.smtp.port"] = 465
        this["mail.smtp.ssl.enable"] = "true"
        this["mail.smtp.ssl.trust"] = "smtp.gmail.com"
    }

    private fun getSession() = Session.getInstance(
        props,
        object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(address, password)
            }
        }
    )

    @Throws(AddressException::class, MessagingException::class)
    override fun sendVerificationEmail(targetEmail: String, code: String) {
        val session = getSession()
        val message = MimeMessage(session)

        message.setFrom(InternetAddress(address))
        message.addRecipient(Message.RecipientType.TO, InternetAddress(targetEmail))
        message.subject = "벙개 - 가입 인증 코드"
        message.setText("벙개 가입을 환영합니다! \n 인증 코드는 [$code] 입니다!")

        Transport.send(message)
    }
}
