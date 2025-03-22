package com.yooshyasha.videoservice.kafka.dto

import java.util.*

data class RequestPublishVideo(
    val videoId: UUID,
    val fileURL: String,
)