package com.frost.lecture.domains.transaction.repository

import com.frost.lecture.types.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionsUser : JpaRepository<User, String> {
    fun findByUlId(id: String): User
}