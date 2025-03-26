package com.yooshyasha.recommendationservice.controllers

import com.yooshyasha.recommendationservice.enities.Video
import com.yooshyasha.recommendationservice.services.RecommendationsService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/videos")
class RecommendationController(
    private val recommendationsService: RecommendationsService,
) {
    @GetMapping
    fun getVideos(@RequestHeader("Authorization") authHeader: String): ResponseEntity<Collection<Video>> {
        try {
            val videos = recommendationsService.getSortedVideoLIst(authHeader)

            return ResponseEntity.ok(videos)
        } catch (e: Exception) {
            LoggerFactory.getLogger(this.javaClass).error(e.message, e)

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @PostMapping("/video")
    fun getVideo(): ResponseEntity<Video?> {
        return ResponseEntity.ok(null)
    }
}