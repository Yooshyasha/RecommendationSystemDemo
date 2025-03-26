package com.yooshyasha.recommendationservice.feign

import com.yooshyasha.recommendationservice.dto.UserDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient("user-service")
interface UserServiceClient {
    @GetMapping("/v1/users/me")
    fun getMe(@RequestHeader("Authorization") authHeader: String) : UserDTO
}