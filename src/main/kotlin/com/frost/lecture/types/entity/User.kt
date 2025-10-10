package com.frost.lecture.types.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "user")
data class User(
    @Id
    @Column(name = "ulid", length = 12, nullable = false)
    val ulId: String,

    @Column(name = "platform", nullable = false, length = 26)
    val platform: String,

    @Column(name = "username", nullable = true, length = 50)
    val username: String,

    @Column(name = "access_token", length = 255)
    val accessToken: String,

    @Column(name = "create_at", nullable = false, updatable = false)
    val createAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "update_at", nullable = false)
    val updateAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "user")
    val accounts: List<Account> = mutableListOf()

)