package com.frost.lecture.domains.bank.service

import com.frost.lecture.common.logging.Logging
import com.frost.lecture.common.transaction.Transactional
import com.frost.lecture.domains.bank.repository.BankAccountRepository
import com.frost.lecture.domains.bank.repository.BankUserRepository
import com.frost.lecture.types.dto.Response
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class BankService(
    private val transactional: Transactional,
    private val userRepository: BankUserRepository,
    private val bankAccountRepository: BankAccountRepository,
    private val logger: Logger = Logging.getLogger(BankService::class.java)
) {
    fun createAccount(userUlid: String): Response<String> = Logging.logFor(logger) {
        log ->
        log["userUlid"] = userUlid
        transactional.run {  }
    }

    fun balance(userUlid: String, accountUlid: String): Response<BigDecimal> = Logging.logFor(logger) {
        log ->
        log["userUlid"] = userUlid
        log["accountUlid"] = accountUlid
        transactional.run {  }
    }

    fun removeAccount(userUlid: String, accountUlid: String): Response<BigDecimal> = Logging.logFor(logger) {
        log ->
        log["userUlid"] = userUlid
        log["accountUlid"] = accountUlid
        transactional.run {  }
    }
}