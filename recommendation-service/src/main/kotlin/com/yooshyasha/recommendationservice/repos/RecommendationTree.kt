package com.yooshyasha.recommendationservice.repos

import com.yooshyasha.recommendationservice.enities.RecommendationTreeVideoEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RecommendationTree : JpaRepository<RecommendationTreeVideoEntity, UUID> {
    fun findByNodeParent(nodeParent: RecommendationTreeVideoEntity) : RecommendationTreeVideoEntity?

    fun findAllByParent(nodeParent: RecommendationTreeVideoEntity) : Collection<RecommendationTreeVideoEntity>
}