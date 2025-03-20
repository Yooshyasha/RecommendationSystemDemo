package com.yooshyasha.videopublishservice.services

import com.yooshyasha.videopublishservice.bo.UserBO
import com.yooshyasha.videopublishservice.feign.UserServiceClient
import org.springframework.stereotype.Service

@Service
class UsersService(
    private val userServiceClient: UserServiceClient,
) {
    fun getMe(token: String) : UserBO {
        return userServiceClient.getMe(token)
    }
}