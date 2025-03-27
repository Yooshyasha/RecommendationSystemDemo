package com.yooshyasha.recommendationservice.repos

import com.yooshyasha.recommendationservice.enities.RecommendationTreeNodeUserEntity
import com.yooshyasha.recommendationservice.enities.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRecommendationTreeRepository : JpaRepository<RecommendationTreeNodeUserEntity, UUID> {
    fun findAllByUser(user: User): Collection<RecommendationTreeNodeUserEntity>

    fun findAllByUser_Id(userId: UUID): Collection<RecommendationTreeNodeUserEntity>

    fun findAllByParent(parent: RecommendationTreeNodeUserEntity): Collection<RecommendationTreeNodeUserEntity>
}