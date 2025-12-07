package com.frost.lecture.types.dto

import com.frost.lecture.common.json.BigDecimalSerializer
import com.frost.lecture.common.json.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

@Serializable
data class History(
    val fromUlid: String,
    val fromUser: String,
    val toUlid: String,
    val toUser: String,

    @Serializable(with = BigDecimalSerializer::class)
    val value: BigDecimal,
    @Serializable(with = LocalDateTimeSerializer::class)
    val time: LocalDateTime
)