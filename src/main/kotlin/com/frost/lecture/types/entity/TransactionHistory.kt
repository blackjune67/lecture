package com.frost.lecture.types.entity

import com.frost.lecture.common.json.BigDecimalSerializer
import com.frost.lecture.common.json.LocalDateTimeSerializer
import com.frost.lecture.types.dto.History
import kotlinx.serialization.Serializable
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime

@Serializable
@Document(collection = "transaction_history")
class TransactionHistoryDocument(
    val fromUlid: String,
    val toUlid: String,

    @Serializable(with = BigDecimalSerializer::class)
    val value: BigDecimal,
    @Serializable(with = LocalDateTimeSerializer::class)
    val time: LocalDateTime
) {

    fun toHistory(fromUser: String, toUser: String): History = History(
        fromUlid = fromUlid,
        fromUser = fromUser,
        toUlid = toUlid,
        toUser = toUser,
        value = value,
        time = time,
    )
}