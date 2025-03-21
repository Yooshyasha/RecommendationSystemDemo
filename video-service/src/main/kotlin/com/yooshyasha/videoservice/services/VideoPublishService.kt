package com.yooshyasha.videoservice.services

import com.yooshyasha.videoservice.entity.Video
import com.yooshyasha.videoservice.kafka.dto.RequestPublishVideo
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class VideoPublishService(
    val template: KafkaTemplate<String, RequestPublishVideo>
) {
    fun publishVideo(video: Video, file: MultipartFile) {
        val requestData = RequestPublishVideo(
            videoId=video.id!!,
            file=file,
        )

        template.send("topic1", requestData)
    }
}