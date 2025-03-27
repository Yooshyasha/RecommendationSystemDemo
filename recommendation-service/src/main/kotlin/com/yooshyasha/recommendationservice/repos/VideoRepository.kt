package com.yooshyasha.recommendationservice.repos

import com.yooshyasha.recommendationservice.enities.Video
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface VideoRepository : JpaRepository<Video, UUID> {

}