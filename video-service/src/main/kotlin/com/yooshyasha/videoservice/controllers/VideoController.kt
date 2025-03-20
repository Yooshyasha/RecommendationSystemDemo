package com.yooshyasha.videoservice.controllers

import com.yooshyasha.videoservice.dto.controllers.RequestPublishVideo
import com.yooshyasha.videoservice.entity.Video
import com.yooshyasha.videoservice.services.VideoService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/videos")
class VideoController(
    private val videoService: VideoService,
) {
    @PostMapping("/video")
    fun postVideo(publishData: RequestPublishVideo): ResponseEntity<Video> {
        try {
            val video = videoService.publishVideo(
                file = publishData.file,
                title = publishData.title,
                description = publishData.description,
                isPublic = publishData.isPublic,
            )

            return ResponseEntity.ok(video)
        } catch (ex: Exception) {
            LoggerFactory.getLogger(this.javaClass).error(ex.message, ex)

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }
}