package com.frost.lecture.domain.auth.service

import com.frost.lecture.config.OAuth2Config
import com.frost.lecture.interfaces.OAuth2TokenResponse
import com.frost.lecture.interfaces.OAuth2UserResponse
import com.frost.lecture.interfaces.OAuthServiceInterface
import org.springframework.stereotype.Service

private const val key = "google"

@Service(key)
class GoogleAuthService(
    private val config: OAuth2Config,
) : OAuthServiceInterface {
    private val oAuthInfo = config.providers[key] ?: throw TODO("Custom Exception")

    override val providerName: String = key

    override fun getToken(code: String): OAuth2TokenResponse {
        TODO("Not yet implemented")
    }

    override fun getUserInfo(accessToken: String): OAuth2UserResponse {
        TODO("Not yet implemented")
    }

}