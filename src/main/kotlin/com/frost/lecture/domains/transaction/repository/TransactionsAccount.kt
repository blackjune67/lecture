package com.frost.lecture.domains.transaction.repository

import com.frost.lecture.types.entity.Account
import com.frost.lecture.types.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionsAccount : JpaRepository<Account, String>{
    fun findByUlidAndUser(ulid: String, user: User): Account?
    fun findByUlId(account: String): Account?
}