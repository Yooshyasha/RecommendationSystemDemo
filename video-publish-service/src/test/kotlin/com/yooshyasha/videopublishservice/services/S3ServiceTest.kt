package com.yooshyasha.videopublishservice.services

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.services.s3.S3Client

@ExtendWith(MockitoExtension::class)
class S3ServiceTest {
    @Mock
    private lateinit var s3Client: S3Client

    @InjectMocks
    private lateinit var s3Service: S3Service

    @BeforeEach
    fun setUp() {
        val bucketField = s3Service::class.java.getDeclaredField("bucket")
        val endpointField = s3Service::class.java.getDeclaredField("endpoint")

        bucketField.isAccessible = true
        endpointField.isAccessible = true

        bucketField.set(s3Service, "bucket1")
        endpointField.set(s3Service, "endpoint1")
    }

    @Test
    fun uploadFile() {
        val file = Mockito.mock(MultipartFile::class.java)

        Mockito.`when`(file.bytes).thenReturn(byteArrayOf())
        Mockito.`when`(file.originalFilename).thenReturn("fileName1")

        val result = s3Service.uploadFile(file)

        assertEquals("endpoint1/bucket1/fileName1", result)
    }
}