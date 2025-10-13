package com.frost.lecture.common.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.*
import com.auth0.jwt.interfaces.DecodedJWT
import com.frost.lecture.common.exception.CustomException
import com.frost.lecture.common.exception.ErrorCode
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtProvider(
    @Value("\${jwt.secret-key}")
    private val secretkey: String,
    @Value("\${jwt.time}")
    private val time: Long
) {
    private val ONE_MINUTE_TO_MILLIS: Long = 60 * 1000

    fun createToken(platform: String, email: String?, name: String?, id: String): String {
        return JWT.create()
            .withSubject("$platform - $email - $name - $id")
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + time * ONE_MINUTE_TO_MILLIS))
            .sign(Algorithm.HMAC256(secretkey))
    }

    fun verifyToken(token: String): DecodedJWT {
        try {
            return JWT.require(Algorithm.HMAC256(secretkey)).build().verify(token)
        } catch (e: AlgorithmMismatchException) {
            throw CustomException(ErrorCode.TOKEN_IS_INVALID, e.message)
        } catch (e: SignatureGenerationException) {
            throw CustomException(ErrorCode.TOKEN_IS_INVALID, e.message)
        } catch (e: InvalidClaimException) {
            throw CustomException(ErrorCode.TOKEN_IS_INVALID, e.message)
        } catch (e: TokenExpiredException) {
            throw CustomException(ErrorCode.TOKEN_IS_EXPIRED, e.message)
        } catch (e: JWTDecodeException) {
            throw CustomException(ErrorCode.TOKEN_IS_INVALID, e.message)
        }
    }
}