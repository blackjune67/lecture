package com.frost.lecture.domains.auth.service

import com.frost.lecture.common.exception.CustomException
import com.frost.lecture.common.exception.ErrorCode
import com.frost.lecture.common.jwt.JwtProvider
import com.frost.lecture.interfaces.OAuthServiceInterface
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val oAuth2Service: Map<String, OAuthServiceInterface>,
    private val jwtProvider: JwtProvider
) {
    fun handleAuth(state: String, code: String): String {
        val provider = state.lowercase()
        val callService = oAuth2Service[provider]
            ?: throw CustomException(ErrorCode.PROVIDER_NOT_FOUND, provider)
        val accessToken = callService.getToken(provider)
        val userInfo = callService.getUserInfo(accessToken.accessToken)
        val token = jwtProvider.createToken(provider, userInfo.email, userInfo.name, userInfo.id)

    }
}