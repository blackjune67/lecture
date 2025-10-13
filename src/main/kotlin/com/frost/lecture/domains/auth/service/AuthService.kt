package com.frost.lecture.domains.auth.service

import com.frost.lecture.common.exception.CustomException
import com.frost.lecture.common.exception.ErrorCode
import com.frost.lecture.common.jwt.JwtProvider
import com.frost.lecture.common.logging.Logging
import com.frost.lecture.common.transaction.Transactional
import com.frost.lecture.interfaces.OAuthServiceInterface
import org.slf4j.Logger
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val oAuth2Service: Map<String, OAuthServiceInterface>,
    private val jwtProvider: JwtProvider,
    private val logger: Logger = Logging.getLogger<AuthService, String>(AuthService::class.java),
    private val transactional: Transactional
) {
    fun handleAuth(state: String, code: String): String = Logging.logFor(logger) {
        val provider = state.lowercase()
        val callService = oAuth2Service[provider]
            ?: throw CustomException(ErrorCode.PROVIDER_NOT_FOUND, provider)
        val accessToken = callService.getToken(code)
        val userInfo = callService.getUserInfo(accessToken.accessToken)
        val token = jwtProvider.createToken(provider, userInfo.email, userInfo.name, userInfo.id)

        transactional.run {  }

    }
}