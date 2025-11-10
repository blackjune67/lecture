package com.frost.lecture.domains.model

import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal

data class DepositRequest (
    @field:NotBlank("enter to accountId")
    val toAccountId: String,

    @field:NotBlank("enter to ulid")
    val toUlId: String,

    @field:NotBlank("enter value")
    val value: BigDecimal,
)

data class TransferRequest(
    @field:NotBlank("enter from ulid")
    val fromUlid: String,

    @field:NotBlank("enter from accountId")
    val fromAccountId: String,

    @field:NotBlank("enter to accountId")
    val toAccountId: String,

    @field:NotBlank("enter value")
    val value: BigDecimal,
)