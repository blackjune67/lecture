package com.frost.lecture.domain.auth.repository

import com.frost.lecture.types.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AuthUserRepository: JpaRepository<User, String> {
    fun existsByUsernameAndPlatform(username: String): Boolean

    @Modifying
    @Query("UPDATE User SET accessToken = :token WHERE username = :username")
    fun updateTokenByUsername(
        @Param("username") username: String,
        @Param("accessToken") token: String,
    )
}