package com.frost.lecture.domains.bank.repository

import com.frost.lecture.types.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface BankAccountRepository : JpaRepository<Account, String> {
    fun findByUlId(ulId: String): Account?
}