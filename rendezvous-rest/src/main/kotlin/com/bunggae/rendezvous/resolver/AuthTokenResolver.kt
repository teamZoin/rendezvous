package com.bunggae.rendezvous.resolver

import com.bunggae.rendezvous.util.authToken.AuthTokenUtil
import com.bunggae.rendezvous.util.authToken.TokenPayload
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class AuthTokenResolver(
    private val authTokenUtil: AuthTokenUtil,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(AuthTokenPayload::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): TokenPayload? {
        val token = webRequest.getHeader("Authorization") ?: throw IllegalArgumentException("Authorization 헤더가 없습니다.")
        return authTokenUtil.decodeToken(token)
    }
}
