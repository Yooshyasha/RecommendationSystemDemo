package com.yooshyasha.videoservice.kafka.dto

import org.springframework.web.multipart.MultipartFile
import java.util.*

data class RequestPublishVideo (
    val videoId: UUID,
    val file: MultipartFile,
)