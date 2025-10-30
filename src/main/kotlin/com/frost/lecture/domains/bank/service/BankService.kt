package com.frost.lecture.domains.bank.service

import com.frost.lecture.common.logging.Logging
import com.frost.lecture.common.transaction.Transactional
import org.springframework.stereotype.Service
import org.slf4j.*
import kotlin.math.log

@Service
class BankService(
    private val transactional: Transactional,
    private val logger: Logger = Logging.getLogger(BankService::class.java)
) {
    fun createAccount(userUlid: String) = Logging.logFor(logger) {
        log ->
        log["userUlid"] = userUlid
        transactional.run {  }
    }

    fun balance(userUlid: String, accountUlid: String) = Logging.logFor(logger) {
        log ->
        log["userUlid"] = userUlid
        log["accountUlid"] = accountUlid
        transactional.run {  }
    }

    fun removeAccount(userUlid: String, accountUlid: String) = Logging.logFor(logger) {
        log ->
        log["userUlid"] = userUlid
        log["accountUlid"] = accountUlid
        transactional.run {  }
    }
}