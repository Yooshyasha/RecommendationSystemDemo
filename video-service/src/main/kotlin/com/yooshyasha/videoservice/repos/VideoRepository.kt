package com.yooshyasha.videoservice.repos

import com.yooshyasha.videoservice.entity.Video
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface VideoRepository : JpaRepository<Video, UUID> {}