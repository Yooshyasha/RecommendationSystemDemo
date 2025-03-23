package com.yooshyasha.videoservice.services

import io.minio.MinioClient
import io.minio.PutObjectArgs
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.util.*

@Service
class TempStorageService(
    private val minioClient: MinioClient,
) {
    @Value("\${minio.endpoint}")
    private lateinit var endpoint: String

    @Value("\${minio.bucket_name}")
    private lateinit var bucketName: String

    fun saveVideo(file: MultipartFile): String {
        val fileName = file.originalFilename
            ?: UUID.randomUUID().toString()

        val inputStream = ByteArrayInputStream(file.bytes)

        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(bucketName)
                .`object`(fileName)
                .stream(inputStream, file.size, -1)
                .contentType("video/mp4")
                .build()
        )

        return "$endpoint/$bucketName/$fileName"
    }
}