package com.frost.lecture.domains.model

import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal

data class DepositRequest (
    @field:NotBlank(message = "enter to accountId")
    val toAccountId: String,

    @field:NotBlank(message = "enter to ulid")
    val toUlId: String,

    @field:NotBlank(message = "enter value")
    val value: BigDecimal,
)

data class TransferRequest(
    @field:NotBlank(message = "enter from ulid")
    val fromUlid: String,

    @field:NotBlank(message = "enter from accountId")
    val fromAccountId: String,

    @field:NotBlank(message = "enter to accountId")
    val toAccountId: String,

    @field:NotBlank(message = "enter value")
    val value: BigDecimal,
)