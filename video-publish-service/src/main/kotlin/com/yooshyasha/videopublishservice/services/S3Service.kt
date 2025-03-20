package com.yooshyasha.videopublishservice.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.*

@Service
class S3Service(
    private val s3Client: S3Client,
) {
    @Value("\${cloud.aws.s3.bucket}")
    private lateinit var bucket: String

    @Value("\${cloud.aws.endpoint}")
    private lateinit var endpoint: String

    fun uploadFile(file: MultipartFile): String {
        val fileName = file.originalFilename
            ?: "file_${UUID.randomUUID()}"

        s3Client.putObject(
            PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build(),
            RequestBody.fromBytes(file.bytes)
        )

        return "$endpoint/$bucket/$fileName"
    }
}