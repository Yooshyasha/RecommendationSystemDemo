package com.yooshyasha.videopublishservice.services

import com.yooshyasha.videopublishservice.minio.FileDTO
import io.minio.GetObjectArgs
import io.minio.MinioClient
import org.springframework.beans.factory.annotation.Value

class TempStorageService(
    private val minioClient: MinioClient,
) {
    @Value("\${minio.bucket_name}")
    private lateinit var bucketName: String

    fun getFile(url: String): FileDTO {
        val objectName = url.split("/").last()

        val response = minioClient.getObject(
            GetObjectArgs.builder()
                .bucket(bucketName)
                .`object`(objectName)
                .build()
        )

        return FileDTO(
            bytes = response.readAllBytes(),
            fileName = response.`object`()
        )
    }
}