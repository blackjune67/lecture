package com.frost.lecture.domains.bank.repository

import com.frost.lecture.types.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface BankUserRepository : JpaRepository<User, String> {
    fun findByUlId(ulId: String): User
}