package com.yooshyasha.recommendationservice.services

import com.yooshyasha.recommendationservice.dto.UserDTO
import com.yooshyasha.recommendationservice.feign.UserServiceClient
import org.springframework.stereotype.Service

@Service
class UsersService(
    private val userServiceClient: UserServiceClient,
) {
    fun getMe(authHeader: String) : UserDTO {
        return userServiceClient.getMe(authHeader)
    }
}