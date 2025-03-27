package com.yooshyasha.recommendationservice.repos

import com.yooshyasha.recommendationservice.enities.RecommendationTreeNodeVideoEntity
import com.yooshyasha.recommendationservice.enities.Video
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface VideoRecommendationTreeRepository : JpaRepository<RecommendationTreeNodeVideoEntity, UUID> {
    fun findAllByVideo(video: Video) : Collection<RecommendationTreeNodeVideoEntity>

    fun findAllByParent(parent: RecommendationTreeNodeVideoEntity) : Collection<RecommendationTreeNodeVideoEntity>
}