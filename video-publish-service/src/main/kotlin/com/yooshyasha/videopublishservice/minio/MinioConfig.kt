package com.yooshyasha.videopublishservice.minio

import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.errors.MinioException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MinioConfig {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Value("\${minio.endpoint}")
    private lateinit var endpoint: String

    @Value("\${minio.access_key}")
    private lateinit var accessKey: String

    @Value("\${minio.secret_key}")
    private lateinit var secretKey: String

    @Value("\${minio.bucket_name}")
    private lateinit var bucketName: String

    @Bean
    fun bucket(): BucketExistsArgs {
        return BucketExistsArgs.builder()
            .bucket(bucketName)
            .build()
    }

    @Bean
    fun minioClient(bucket: BucketExistsArgs): MinioClient {
        val client = MinioClient.builder()
            .endpoint(endpoint)
            .credentials(accessKey, secretKey)
            .build()

        try {
            if (!client.bucketExists(bucket)) {
                logger.info("Bucket was not found")
                client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build())
            } else {
                logger.info("Bucket was been found")
            }
        } catch (e: MinioException) {
            logger.error("Error occupied: ", e)
        }

        return client
    }
}