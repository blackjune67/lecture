package com.frost.lecture.domains.history.respository

import com.frost.lecture.types.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface HistoryUserRepository: JpaRepository<User, String> {
    fun findByUlId(ulId: String): User
}