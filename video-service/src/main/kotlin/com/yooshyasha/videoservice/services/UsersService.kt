package com.yooshyasha.videoservice.services

import com.yooshyasha.videoservice.bo.UserBO
import com.yooshyasha.videoservice.feign.UsersServiceClient
import org.springframework.stereotype.Service

@Service
class UsersService(
    private val usersServiceClient: UsersServiceClient
) {
    fun getMe(token: String) : UserBO {
        return usersServiceClient.getMe(token)
    }
}