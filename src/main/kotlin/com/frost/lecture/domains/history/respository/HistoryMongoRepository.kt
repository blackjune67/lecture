package com.frost.lecture.domains.history.respository

import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class HistoryMongoRepository(
    private val template: HashMap<String, MongoTemplate>,
    private val historyUserRepository: HistoryUserRepository
) {

    fun findLatestTransactionHistory(ulid: String, limit: Int = 30) {
        val criteria = Criteria().orOperator(
            Criteria.where("fromUlId").`is`(ulid),
            Criteria.where("toUlId").`is`(ulid),
        )

        val query = Query(criteria)
            .with(Sort.by(Sort.Direction.DESC, "time"))
            .limit(limit)

        query.fields().exclude("_id")
    }
}