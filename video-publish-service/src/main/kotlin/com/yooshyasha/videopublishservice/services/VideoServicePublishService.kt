package com.yooshyasha.videopublishservice.services

import com.yooshyasha.videopublishservice.entity.Video
import com.yooshyasha.videopublishservice.enums.VideoStatus
import com.yooshyasha.videopublishservice.repos.VideoRepository
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class VideoServicePublishService(
    private val videoRepository: VideoRepository,
    private val s3Service: S3Service,
    private val usersService: UsersService,
) {
    fun publishVideo(video: Video, file: MultipartFile): Boolean {
        val fileUrl = s3Service.uploadFile(file)

        video.status = VideoStatus.PUBLISHED
        video.fileUrl = fileUrl
        saveVideo(video)

        return true
    }

    fun publishVideo(videoId: UUID, file: MultipartFile): Boolean {
        val video = videoRepository.findById(videoId).orElseThrow()

        return publishVideo(video, file)
    }

    fun saveVideo(video: Video) {
        videoRepository.save(video)
    }

    fun validateAccessToPublish(token: String, videoId: UUID): Boolean {
        val me = usersService.getMe(token)
        val video = videoRepository.findById(videoId).orElseThrow()

        if (me.id != video.authorId) throw AccessDeniedException("Access denied")

        return true
    }
}