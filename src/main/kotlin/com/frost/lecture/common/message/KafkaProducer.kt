package com.frost.lecture.common.message

import com.frost.lecture.common.exception.CustomException
import com.frost.lecture.common.exception.ErrorCode
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class KafkaProducer(
    private val template: KafkaTemplate<String, Any>,
    private val log: Logger = LoggerFactory.getLogger(KafkaProducer::class.java)
) {

    fun sendMessage(topic: String, message: Any) {
        val future = template.send(topic, message)
        future.whenComplete { result, ex ->
            if(ex == null) {
                // 메시지 전송 성공
                log.info("메시지 발행 성공 - topic: $topic - time: ${LocalDateTime.now()}")
            } else {
                log.error("메시지 전송 실패 - ${ex.message}")
                throw CustomException(ErrorCode.FAILED_TO_SEND_MESSAGE, topic)
            }
        }
    }

}