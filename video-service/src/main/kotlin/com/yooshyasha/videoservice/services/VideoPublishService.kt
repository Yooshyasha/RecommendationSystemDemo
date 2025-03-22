package com.yooshyasha.videoservice.services

import com.yooshyasha.videoservice.entity.Video
import com.yooshyasha.videoservice.kafka.dto.RequestPublishVideo
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class VideoPublishService(
    private val template: KafkaTemplate<String, RequestPublishVideo>,
    private val tempStorageService: TempStorageService,
) {
    fun publishVideo(video: Video, file: MultipartFile) {
        val url = tempStorageService.saveVideo(file)

        val requestData = RequestPublishVideo(
            videoId = video.id!!,
            fileURL = url
        )

        template.send("publish-video", requestData)
    }
}