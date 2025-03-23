package com.yooshyasha.videopublishservice

import com.yooshyasha.videopublishservice.kafka.dto.RequestPublishVideo
import com.yooshyasha.videopublishservice.services.VideoServicePublishService
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumerService(
    private val videoServicePublishService: VideoServicePublishService,
) {
    @KafkaListener(groupId = "video-communications", topics = ["publish-video"], containerFactory = "consumerContainer")
    fun publishVideoListener(requestPublishVideo: RequestPublishVideo) {
        LoggerFactory.getLogger(this.javaClass).info("New request: $requestPublishVideo")

        try {
            val result =
                videoServicePublishService.publishVideo(requestPublishVideo.videoId, requestPublishVideo.fileURL)

            if (!result) throw Exception("Unknown exception")
        } catch (e: Exception) {
            LoggerFactory.getLogger(this.javaClass).error("Exception: ", e)
            throw e
        }
    }
}