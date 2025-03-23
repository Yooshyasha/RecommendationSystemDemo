package com.yooshyasha.videopublishservice.repos

import com.yooshyasha.videopublishservice.entity.Video
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface VideoRepository : JpaRepository<Video, UUID> {}