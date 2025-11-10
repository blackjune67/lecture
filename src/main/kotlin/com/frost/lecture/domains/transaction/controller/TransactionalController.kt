package com.frost.lecture.domains.transaction.controller

import com.frost.lecture.domains.model.DepositRequest
import com.frost.lecture.domains.model.DepositResponse
import com.frost.lecture.domains.model.TransferRequest
import com.frost.lecture.domains.model.TransferResponse
import com.frost.lecture.domains.transaction.service.TransactionService
import com.frost.lecture.types.dto.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/transactional")
class TransactionalController(
    private val transactionService: TransactionService
) {

    @PostMapping("/deposit")
    fun deposit(@RequestBody(required = true) request: DepositRequest): Response<DepositResponse> {
        return transactionService.deposit(request.toUlId, request.toAccountId, request.value)
    }

    @PostMapping("/transfer")
    fun transfer(@RequestBody(required = true) request: TransferRequest): Response<TransferResponse> {
        return transactionService.transfer(request.fromUlid, request.fromAccountId, request.toAccountId, request.value)
    }
    //TODO
}