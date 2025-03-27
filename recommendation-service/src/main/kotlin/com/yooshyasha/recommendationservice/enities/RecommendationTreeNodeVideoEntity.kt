package com.yooshyasha.recommendationservice.enities

import jakarta.annotation.Nullable
import jakarta.persistence.*
import java.util.*

@Entity
data class RecommendationTreeNodeVideoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @OneToOne(targetEntity = Video::class)
    val video: Video? = null,

    val value: Float = 0.0f,

    @ManyToOne(targetEntity = RecommendationTreeNodeVideoEntity::class)
    @Nullable
    var parent: RecommendationTreeNodeVideoEntity? = null,
) {
    fun getPayload(): Any {
        return value
    }

    fun toStringTree(): String {
        return this.toString()
    }
}
