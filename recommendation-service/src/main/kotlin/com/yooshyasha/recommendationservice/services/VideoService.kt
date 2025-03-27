package com.yooshyasha.recommendationservice.services

import com.yooshyasha.recommendationservice.enities.Video
import com.yooshyasha.recommendationservice.repos.VideoRepository
import org.springframework.stereotype.Service

@Service
class VideoService(
    private val videoRepository: VideoRepository,
) {
    fun getAllVideos() : Collection<Video> {
        return videoRepository.findAll()
    }
}