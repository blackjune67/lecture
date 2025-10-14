package com.frost.lecture.domains.auth.service

import com.frost.lecture.common.exception.CustomException
import com.frost.lecture.common.exception.ErrorCode
import com.frost.lecture.common.jwt.JwtProvider
import com.frost.lecture.common.logging.Logging
import com.frost.lecture.common.transaction.Transactional
import com.frost.lecture.domains.auth.repository.AuthUserRepository
import com.frost.lecture.interfaces.OAuthServiceInterface
import com.frost.lecture.types.entity.User
import com.github.f4b6a3.ulid.UlidCreator
import org.slf4j.Logger
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val oAuth2Service: Map<String, OAuthServiceInterface>,
    private val jwtProvider: JwtProvider,
    private val logger: Logger = Logging.getLogger<AuthService, String>(AuthService::class.java),
    private val transactional: Transactional,
    private val authUserRepository: AuthUserRepository,
) {
    fun handleAuth(state: String, code: String): String = Logging.logFor(logger) { log ->
        val provider = state.lowercase()
        log["provider"] = provider
        val callService = oAuth2Service[provider]
            ?: throw CustomException(ErrorCode.PROVIDER_NOT_FOUND, provider)
        val accessToken = callService.getToken(code)
        val userInfo = callService.getUserInfo(accessToken.accessToken)
        val token = jwtProvider.createToken(provider, userInfo.email, userInfo.name, userInfo.id)
        val username = (userInfo.name ?: userInfo.email).toString()
        log["username"] = username

        transactional.run {
            val exist = authUserRepository.existsByUsername(username)
            if (exist) {
                // access Token만 update
                authUserRepository.updateTokenByUsername(username, token)
            } else {
                // ulid
                val ulid = UlidCreator.getUlid().toString()
                val user = User(ulid, username, token)
                authUserRepository.save(user)
            }
        }

        return@logFor token
    }

    fun verifyToken(authHeader: String) {
        jwtProvider.verifyToken(authHeader.removePrefix("Bearer ").trim())
    }
}