package com.frost.lecture.config

import com.frost.lecture.common.exception.CustomException
import com.frost.lecture.common.exception.ErrorCode
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ReadPreference
import com.mongodb.client.MongoClients
import org.bson.UuidRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory

enum class MongoTableCollector(
    val table: String
) {
    Bank("bank"),
}

@Configuration
@EnableMongoAuditing
class MongoConfig(
    @Value("\${database.mongo.uri}") private val url: String,
) {
    @Bean
    fun template(): HashMap<String, MongoTemplate> {
        val mapper = HashMap<String, MongoTemplate>(MongoTableCollector.entries.size)
        val settings = MongoClientSettings.builder()
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .applyConnectionString(ConnectionString(url))
            .readPreference(ReadPreference.primary())
            .build()

        for (c in MongoTableCollector.entries) {
            try {
                val client = MongoClients.create(settings)
                mapper[c.table] = MongoTemplate(SimpleMongoClientDatabaseFactory(client, c.table))
            } catch (e: Exception) {
                throw CustomException(ErrorCode.FAILED_TO_CONNECT_MONGO)
            }
        }
        return mapper
    }
}