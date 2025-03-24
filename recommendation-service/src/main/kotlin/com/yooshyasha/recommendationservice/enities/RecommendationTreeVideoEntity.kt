package com.yooshyasha.recommendationservice.enities

import jakarta.annotation.Nullable
import jakarta.persistence.*
import org.antlr.v4.runtime.tree.Tree
import java.util.*

@Entity
data class RecommendationTreeVideoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @OneToOne(targetEntity = Video::class)
    val video: Video? = null,

    val value: Float = 0.0f,

    @ManyToOne(targetEntity = RecommendationTreeVideoEntity::class)
    @Nullable
    val nodeParent: RecommendationTreeVideoEntity? = null,
) : Tree {
    override fun getParent(): RecommendationTreeVideoEntity? {
        return nodeParent
    }

    override fun getPayload(): Any {
        return value
    }

    override fun getChild(p0: Int): Tree {
        return this
    }

    override fun getChildCount(): Int {
        return 0
    }

    override fun toStringTree(): String {
        return this.toString()
    }

}
