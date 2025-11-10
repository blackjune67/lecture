package com.frost.lecture.domains.transaction.service

import com.frost.lecture.common.cache.RedisClient
import com.frost.lecture.common.cache.RedisKeyProvider
import com.frost.lecture.common.exception.CustomException
import com.frost.lecture.common.exception.ErrorCode
import com.frost.lecture.common.logging.Logging
import com.frost.lecture.common.transaction.Transactional
import com.frost.lecture.domains.model.DepositResponse
import com.frost.lecture.domains.transaction.repository.TransactionsAccount
import com.frost.lecture.domains.transaction.repository.TransactionsUser
import com.frost.lecture.types.dto.Response
import com.frost.lecture.types.dto.ResponseProvider
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class TransactionService(
    private val transactionsUser: TransactionsUser,
    private val transactionsAccount: TransactionsAccount,
    private val redisClient: RedisClient,
    private val transactional: Transactional,
    private val logger: Logger = Logging.getLogger(TransactionService::class.java)
) {
    fun deposit(userUlid: String, accountId: String, value: BigDecimal): Response<DepositResponse> = Logging.logFor(logger) {
        it
        it["userUlid"] = userUlid
        it["accountId"] = accountId
        it["value"] = value

        val key = RedisKeyProvider.bankMutexKey(userUlid, accountId)

        return@logFor redisClient.invokeWithMutex(key) {
            return@invokeWithMutex transactional.run {
                val user = transactionsUser.findByUlId(userUlid)
                val account = transactionsAccount.findByUlidAndUser(accountId, user)
                    ?:throw CustomException(ErrorCode.FAILED_TO_FIND_ACCOUNT)
                account.balance = account.balance.add(value)
                account.updateAt = LocalDateTime.now()
                transactionsAccount.save(account)

                ResponseProvider.success(DepositResponse(afterBalance = account.balance))
            }
        }

    }
}