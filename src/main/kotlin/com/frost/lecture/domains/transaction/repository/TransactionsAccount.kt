package com.frost.lecture.domains.transaction.repository

import com.frost.lecture.types.entity.Account
import com.frost.lecture.types.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionsAccount : JpaRepository<Account, String>{
    fun findByUlIdAndUser(ulId: String, user: User): Account?
    fun findByUlId(accountUlId: String): Account?
}