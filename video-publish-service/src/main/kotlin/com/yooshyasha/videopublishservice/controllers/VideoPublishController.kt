package com.yooshyasha.videopublishservice.controllers

import com.yooshyasha.videopublishservice.dto.controllers.RequestPublishVideo
import com.yooshyasha.videopublishservice.services.VideoServicePublishService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/publish")
class VideoPublishController(
    private val videoServicePublishService: VideoServicePublishService,
) {
    @PostMapping("/video")
    fun publishVideo(publishData: RequestPublishVideo, request: HttpServletRequest): ResponseEntity<Boolean> {
        val authHeader = request.getHeader("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        val token = authHeader.substring(7)

        if (!videoServicePublishService.validateAccessToPublish(token, publishData.videoId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

        val result = videoServicePublishService.publishVideo(publishData.videoId, publishData.file)

        return ResponseEntity.ok(result)

    }
}