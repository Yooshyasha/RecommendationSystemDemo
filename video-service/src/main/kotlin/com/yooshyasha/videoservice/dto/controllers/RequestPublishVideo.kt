package com.yooshyasha.videoservice.dto.controllers

import org.springframework.web.multipart.MultipartFile

data class RequestPublishVideo (
    val file: MultipartFile,
    val title: String,
    val description: String,
    val isPublic: Boolean,
)