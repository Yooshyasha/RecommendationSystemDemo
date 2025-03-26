package com.yooshyasha.recommendationservice.enities

import jakarta.annotation.Nullable
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.util.*

data class RecommendationTreeNodeUserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(targetEntity = User::class)
    val user: User? = null,

    val value: Float = 0.0f,

    @ManyToOne(targetEntity = RecommendationTreeNodeUserEntity::class)
    @Nullable
    val parent: RecommendationTreeNodeUserEntity? = null,
)
