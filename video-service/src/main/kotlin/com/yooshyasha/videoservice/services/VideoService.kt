package com.yooshyasha.videoservice.services

import com.yooshyasha.videoservice.entity.Video
import com.yooshyasha.videoservice.repos.VideoRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class VideoService(
    private val s3Service: S3Service,
    private val usersService: UsersService,
    private val videoRepository: VideoRepository,
) {
    fun publishVideo(
        file: MultipartFile,
        title: String,
        description: String,
        isPublic: Boolean,
        authorId: UUID
    ): Video {
        val url = s3Service.uploadFile(file)

        val video = Video(
            isPublic = isPublic,
            authorId = authorId,
            title = title,
            description = description,
            fileUrl = url
        )

        saveVideo(video)

        return video
    }

    fun publishVideo(
        file: MultipartFile,
        title: String,
        description: String,
        isPublic: Boolean
    ): Video {
        val name = SecurityContextHolder.getContext().authentication.name
        val me = usersService.getUserByUsername(name)

        return publishVideo(file, title, description, isPublic, me.id)
    }

    fun saveVideo(video: Video) {
        videoRepository.save(video)
    }
}