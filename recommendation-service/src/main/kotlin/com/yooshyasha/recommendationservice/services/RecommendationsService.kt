package com.yooshyasha.recommendationservice.services

import com.yooshyasha.recommendationservice.enities.RecommendationTreeNodeUserEntity
import com.yooshyasha.recommendationservice.enities.RecommendationTreeNodeVideoEntity
import com.yooshyasha.recommendationservice.enities.User
import com.yooshyasha.recommendationservice.enities.Video
import com.yooshyasha.recommendationservice.feign.UserServiceClient
import com.yooshyasha.recommendationservice.repos.UserRecommendationTreeRepository
import com.yooshyasha.recommendationservice.repos.VideoRecommendationTreeRepository
import org.springframework.stereotype.Service

@Service
class RecommendationsService(
    private val videoRecommendationTreeRepository: VideoRecommendationTreeRepository,
    private val userRecommendationTreeRepository: UserRecommendationTreeRepository,
    private val videoService: VideoService,
    private val userServiceClient: UserServiceClient,
) {
    fun getFullUserRecommendationTree(recommendationTreeRoot: RecommendationTreeNodeUserEntity): Collection<Float> {
        val fullTree: MutableSet<Float> = mutableSetOf()
        val currentNode: RecommendationTreeNodeUserEntity = recommendationTreeRoot

        while (true) {
            val currentChildren = userRecommendationTreeRepository.findAllByParent(currentNode)

            if (currentChildren.isEmpty()) break

            fullTree.addAll(currentChildren.map { it.value }.toList())
        }

        return fullTree
    }

    fun getFullVideoRecommendationTree(recommendationTreeRoot: RecommendationTreeNodeVideoEntity): Collection<Float> {
        val fullTree: MutableSet<Float> = mutableSetOf()
        val currentNode: RecommendationTreeNodeVideoEntity = recommendationTreeRoot

        while (true) {
            val currentChildren = videoRecommendationTreeRepository.findAllByParent(currentNode)

            if (currentChildren.isEmpty()) {
                break
            }

            fullTree.addAll(currentChildren.map { it.value }.toList())
        }

        return fullTree
    }

    fun matchesTrees(userTree: Collection<Float>, videoTree: Collection<Float>): Double {
        val layerMultiplier = 0.7
        val availableOffset = 10.0
        var matchesRecord = 0.0

        val (mostSizeList, lessSizeList) = if (userTree.size >= videoTree.size) {
            userTree.toList() to videoTree.toList()
        } else {
            videoTree.toList() to userTree.toList()
        }

        for (index in lessSizeList.indices) {
            val fl = mostSizeList[index]
            val compareValue = lessSizeList[index]

            if (compareValue in (fl - availableOffset)..(fl + availableOffset)) {
                val currentMultiplier = if (index == 0) 1.0 else layerMultiplier
                matchesRecord += currentMultiplier
            }
        }

        return matchesRecord
    }

    fun getSortedVideoList(userTree: Collection<Float>): List<Video> {
        val videos = videoService.getAllVideos()

        return videos.sortedBy { video ->
            val rootRecommendationTree = videoRecommendationTreeRepository.findAllByVideo(video)
                .sortedByDescending { it.parent == null }

            if (rootRecommendationTree.isEmpty()) return@sortedBy Double.MAX_VALUE

            val recommendationTree = getFullVideoRecommendationTree(rootRecommendationTree.first())

            matchesTrees(userTree, recommendationTree)
        }
    }

    fun getSortedVideoLIst(authHeader: String): List<Video> {
        val user = userServiceClient.getMe(authHeader)

        val userTreeNodes = userRecommendationTreeRepository.findAllByUser_Id(user.id).map { it.value }

        return getSortedVideoList(userTreeNodes)
    }

    fun generateVideoTree(video: Video): Collection<Float> {
        var latestNode: RecommendationTreeNodeVideoEntity = RecommendationTreeNodeVideoEntity(video = video, value = 1f)
        for (i in (0..10)) {
            videoRecommendationTreeRepository.save(latestNode)
            latestNode = RecommendationTreeNodeVideoEntity(video = video, value = 2f, parent = latestNode)
        }

        return getFullVideoRecommendationTree(
            videoRecommendationTreeRepository.findAllByVideo(video)
                .maxByOrNull { it.parent == null }!!
        )
    }

//    fun markVideoAsWatched(video: Video, user: User) {
//        TODO()
//    }
}