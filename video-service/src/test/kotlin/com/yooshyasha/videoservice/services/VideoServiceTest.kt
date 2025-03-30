package com.yooshyasha.videoservice.services

import com.yooshyasha.videoservice.bo.UserBO
import com.yooshyasha.videoservice.repos.VideoRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.quality.Strictness
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.multipart.MultipartFile
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class VideoServiceTest {
    @Mock
    private lateinit var s3Service: S3Service

    @Mock
    private lateinit var usersService: UsersService

    @Mock
    private lateinit var videoRepository: VideoRepository

    @InjectMocks
    private lateinit var videoService: VideoService

    @Test
    fun publishVideo() {
        val user = UserBO(id = UUID.randomUUID(), name = "yooshyasha")
        val file = Mockito.mock(MultipartFile::class.java)

        val auth = Mockito.mock(Authentication::class.java)
        val securityContext = Mockito.mock(SecurityContext::class.java)
        SecurityContextHolder.setContext(securityContext)

        Mockito.`when`(file.bytes).thenReturn(byteArrayOf())

        Mockito.`when`(auth.name).thenReturn(user.name)
        Mockito.`when`(securityContext.authentication).thenReturn(auth)

        Mockito.`when`(usersService.getUserByUsername(user.name)).thenReturn(user)
        Mockito.`when`(s3Service.uploadFile(file)).thenReturn("video-temp-url")

        val result = videoService.publishVideo(file, "Title", "Description", true)

        assertEquals("video-temp-url", result.fileUrl)
        assertEquals(user.id, result.authorId)
        assertEquals("Title", result.title)
        assertEquals("Description", result.description)
        assertTrue(result.isPublic)
    }
}