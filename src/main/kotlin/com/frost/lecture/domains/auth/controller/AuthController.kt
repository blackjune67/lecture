package com.frost.lecture.domains.auth.controller

import com.frost.lecture.domains.auth.service.AuthService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    @GetMapping("/callback")
    fun callback(
        @RequestParam(value = "code", required = true) code: String,
        @RequestParam(value = "state", required = true) state: String,
        response: HttpServletResponse
    ): ResponseEntity<Any> {
        val token: String = authService.handleAuth(state, code)
        response.addCookie(
            Cookie("authToken", token).apply {
                isHttpOnly = true
                path = "/"
                maxAge = 60 * 60 * 24 * 7
            }
        )

        // TODO -> craete url을 config로 추후 관리
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create("https://localhost:3000")).build()
        /*return  ResponseEntity
            .status(HttpStatus.FOUND)
            .header("Set-Cookie", "authToken=$token; HttpOnly; Path=/; Max-Age=${60 * 60 * 24 * 7}")
            .location(URI.create("http://localhost:3000"))
            .build()*/
    }

    @GetMapping("/verify-token")
    fun verifyToken(
        @RequestParam("Authorization", required = true) authHeader: String
    ) {
        return authService.verifyToken(authHeader)
    }

}