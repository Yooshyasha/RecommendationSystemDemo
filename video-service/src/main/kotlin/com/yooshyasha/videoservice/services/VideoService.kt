package com.yooshyasha.videoservice.services

import com.yooshyasha.videoservice.entity.Video
import com.yooshyasha.videoservice.repos.VideoRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class VideoService(
    private val usersService: UsersService,
    private val videoRepository: VideoRepository,
    private val videoPublishService: VideoPublishService,
) {
    fun requestPublishVideo(
        file: MultipartFile,
        title: String,
        description: String,
        isPublic: Boolean,
        authorId: UUID
    ): Video {

        val video = Video(
            isPublic = isPublic,
            authorId = authorId,
            title = title,
            description = description,
        )

        saveVideo(video)

        videoPublishService.publishVideo(video, file)

        return video
    }

    fun requestPublishVideo(
        file: MultipartFile,
        title: String,
        description: String,
        isPublic: Boolean
    ): Video {
        val name = SecurityContextHolder.getContext().authentication.name
        val me = usersService.getUserByUsername(name)

        return requestPublishVideo(file, title, description, isPublic, me.id)
    }

    fun saveVideo(video: Video) {
        videoRepository.save(video)
    }
}