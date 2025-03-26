package com.yooshyasha.recommendationservice.controllers

import com.yooshyasha.recommendationservice.enities.Video
import com.yooshyasha.recommendationservice.services.VideoService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/videos")
class RecommendationController(
    private val videoService: VideoService,
) {
    @GetMapping
    fun getVideos(@RequestHeader("Authorization") authHeader: String) : ResponseEntity<Collection<Video>> {
        val token = authHeader.substring(7)

        try {
            val videos = videoService.getAllVideos()

            return ResponseEntity.ok(videos)
        } catch (e: Exception) {
            LoggerFactory.getLogger(this.javaClass).error(e.message, e)

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @PostMapping("/video")
    fun getVideo() : ResponseEntity<Video?> {
        return ResponseEntity.ok(null)
    }
}