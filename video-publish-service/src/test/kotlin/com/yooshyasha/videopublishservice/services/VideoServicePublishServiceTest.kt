package com.yooshyasha.videopublishservice.services

import com.yooshyasha.videopublishservice.bo.UserBO
import com.yooshyasha.videopublishservice.entity.Video
import com.yooshyasha.videopublishservice.enums.VideoStatus
import com.yooshyasha.videopublishservice.minio.FileDTO
import com.yooshyasha.videopublishservice.repos.VideoRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.access.AccessDeniedException
import java.util.*

@ExtendWith(MockitoExtension::class)
class VideoServicePublishServiceTest {
    @Mock
    private lateinit var videoRepository: VideoRepository

    @Mock
    private lateinit var s3Service: S3Service

    @Mock
    private lateinit var usersService: UsersService

    @Mock
    private lateinit var tempStorageService: TempStorageService

    @InjectMocks
    private lateinit var videoServicePublishService: VideoServicePublishService

    @Test
    fun publishVideoTestWithMultipartFile() {
        val video = Video(id = UUID.randomUUID())
        val file = MockMultipartFile("fileName.mp4", "content".toByteArray())

        Mockito.`when`(s3Service.uploadFile(file)).thenReturn("fileUrl")

        val result = videoServicePublishService.publishVideo(video, file)

        assertTrue(result)
        assertEquals(VideoStatus.PUBLISHED, video.status)
        assertEquals("fileUrl", video.fileUrl)
    }

    @Test
    fun publishVideoTestWithByteArray() {
        val video = Video(id = UUID.randomUUID())
        val fileBytes = "content".toByteArray()

        Mockito.`when`(s3Service.uploadFile(fileBytes)).thenReturn("fileUrl")

        val result = videoServicePublishService.publishVideo(video, fileBytes)

        assertTrue(result)
        assertEquals(VideoStatus.PUBLISHED, video.status)
        assertEquals("fileUrl", video.fileUrl)
    }

    @Test
    fun publishVideoTestWithTempUrl() {
        val video = Video(id = UUID.randomUUID())
        val fileDto = FileDTO(bytes = "content".toByteArray(), fileName = "tempFileUrl")

        Mockito.`when`(videoRepository.findById(video.id!!)).thenReturn(Optional.of(video))
        Mockito.`when`(tempStorageService.getFile("tempFileUrl")).thenReturn(fileDto)
        Mockito.`when`(s3Service.uploadFile(fileDto.bytes)).thenReturn("fileUrl")

        val result = videoServicePublishService.publishVideo(video.id!!, "tempFileUrl")

        assertTrue(result)
        assertEquals(VideoStatus.PUBLISHED, video.status)
        assertEquals("fileUrl", video.fileUrl)
    }

    @Test
    fun validateAccessToPublishTestSuccess() {
        val user = UserBO(id = UUID.randomUUID(), name = "yooshyasha")
        val video = Video(id = UUID.randomUUID(), authorId = user.id)

        Mockito.`when`(usersService.getMe("jwtToken")).thenReturn(user)
        Mockito.`when`(videoRepository.findById(video.id!!)).thenReturn(Optional.of(video))

        val result = videoServicePublishService.validateAccessToPublish("jwtToken", video.id!!)

        assertTrue(result)
    }

    @Test
    fun validateAccessToPublishTestFailed() {
        val user = UserBO(id = UUID.randomUUID(), name = "yooshyasha")

        val video = Video(id = UUID.randomUUID(), authorId = UUID.randomUUID())

        Mockito.`when`(usersService.getMe("jwtToken")).thenReturn(user)
        Mockito.`when`(videoRepository.findById(video.id!!)).thenReturn(Optional.of(video))

        assertThrows(AccessDeniedException::class.java, {
            videoServicePublishService.validateAccessToPublish("jwtToken", video.id!!)
        })
    }
}