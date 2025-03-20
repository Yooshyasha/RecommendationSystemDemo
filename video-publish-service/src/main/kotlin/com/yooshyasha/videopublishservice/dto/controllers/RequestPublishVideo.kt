package com.yooshyasha.videopublishservice.dto.controllers

import org.springframework.web.multipart.MultipartFile
import java.util.UUID

data class RequestPublishVideo (
    val videoId: UUID,
    val file: MultipartFile,
)