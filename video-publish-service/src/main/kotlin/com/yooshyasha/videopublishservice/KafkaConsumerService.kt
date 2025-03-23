package com.yooshyasha.videopublishservice

import com.yooshyasha.videopublishservice.kafka.dto.RequestPublishVideo
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumerService {
    @KafkaListener(groupId = "video-communications", topics = ["publish-video"], containerFactory = "consumerContainer")
    fun publishVideoListener(requestPublishVideo: RequestPublishVideo) {
        LoggerFactory.getLogger(this.javaClass).info("New request: $requestPublishVideo")
    }
}