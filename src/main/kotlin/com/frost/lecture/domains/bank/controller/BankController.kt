package com.frost.lecture.domains.bank.controller

import com.frost.lecture.domains.bank.service.BankService
import com.frost.lecture.types.dto.Response
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/v1/bank")
class BankController(
    private val bankService: BankService,
) {

    @PostMapping("/create/{userUlid}")
    fun createAccount(
        @PathVariable("userUlid", required = true) userUlid: String,
    ): Response<String> {
        return bankService.createAccount(userUlid)
    }

    @GetMapping("/balance/{userid}/{accountUlid}")
    fun balance(
        @PathVariable("userUlid", required = true) userUlid: String,
        @PathVariable("accountUlid", required = true) accountUlid: String,
    ): Response<BigDecimal> {
        return bankService.balance(userUlid, accountUlid)
    }


    @PostMapping("/balance/{userid}/{accountUlid}")
    fun removeAccount(
        @PathVariable("userUlid", required = true) userUlid: String,
        @PathVariable("accountUlid", required = true) accountUlid: String,
    ): Response<BigDecimal> {
        return bankService.removeAccount(userUlid, accountUlid)
    }
}