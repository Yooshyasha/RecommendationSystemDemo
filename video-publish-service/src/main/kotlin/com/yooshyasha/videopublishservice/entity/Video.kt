package com.yooshyasha.videopublishservice.entity

import com.yooshyasha.videopublishservice.enums.VideoStatus
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
    var fileUrl: String? = null,

    var status: VideoStatus = VideoStatus.UPLOADING
)