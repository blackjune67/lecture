package com.frost.lecture.domains.bank.service

import com.frost.lecture.common.exception.CustomException
import com.frost.lecture.common.exception.ErrorCode
import com.frost.lecture.common.logging.Logging
import com.frost.lecture.common.transaction.Transactional
import com.frost.lecture.domains.bank.repository.BankAccountRepository
import com.frost.lecture.domains.bank.repository.BankUserRepository
import com.frost.lecture.types.dto.Response
import com.frost.lecture.types.dto.ResponseProvider
import com.frost.lecture.types.entity.Account
import com.github.f4b6a3.ulid.UlidCreator
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.lang.Math.random

@Service
class BankService(
    private val transactional: Transactional,
    private val bankUserRepository: BankUserRepository,
    private val bankAccountRepository: BankAccountRepository,
    private val logger: Logger = Logging.getLogger(BankService::class.java)
) {
    fun createAccount(userUlid: String): Response<String> = Logging.logFor(logger) {
        log ->
        log["userUlid"] = userUlid
        transactional.run {
            val user = bankUserRepository.findByUlId(userUlid)
            val ulid = UlidCreator.getUlid().toString()
            val accountNumber = generateRandomAccountNumber()

            val account = Account(
                ulId = ulid,
                user = user,
                accountNumber = accountNumber,
            )
            try {
                bankAccountRepository.save(account)
            } catch (e: Exception) {
                throw CustomException(ErrorCode.FAILED_TO_SAVE_DATA, e.message)
            }
        }

        return@logFor ResponseProvider.success("SUCCESS")
    }

    fun balance(userUlid: String, accountUlid: String): Response<BigDecimal> = Logging.logFor(logger) {
        log ->
        log["userUlid"] = userUlid
        log["accountUlid"] = accountUlid
        transactional.run {  }
    }

    fun removeAccount(userUlid: String, accountUlid: String): Response<String> = Logging.logFor(logger) {
        log ->
        log["userUlid"] = userUlid
        log["accountUlid"] = accountUlid
        transactional.run {  }

        return@logFor ResponseProvider.success("SUCCESS")
    }

    private fun generateRandomAccountNumber(): String {
        val bankCode = "003"
        val section = "12"
        val number = random().toString()
        return "$bankCode-$section-$number"
    }
}