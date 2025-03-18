package com.yooshyasha.videoservice.controllers

import com.yooshyasha.videoservice.services.S3Service
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/videos")
class VideoController(
    private val s3Service: S3Service,
) {
    @PostMapping("/video")
    fun postVideo(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        return ResponseEntity.ok(s3Service.uploadFile(file))
    }
}