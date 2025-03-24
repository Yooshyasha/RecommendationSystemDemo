package com.yooshyasha.recommendationservice.enities

import com.yooshyasha.recommendationservice.enums.VideoStatus
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.*

@Entity
data class Video(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    val isPublic: Boolean = true,
    val authorId: UUID? = null,
    val title: String? = null,
    val description: String? = null,
    val fileUrl: String? = null,

    val status: VideoStatus = VideoStatus.UPLOADING
)