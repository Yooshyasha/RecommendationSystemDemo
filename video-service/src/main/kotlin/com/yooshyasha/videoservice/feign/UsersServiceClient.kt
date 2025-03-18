package com.yooshyasha.videoservice.feign

import com.yooshyasha.videoservice.bo.UserBO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient("user-service")
interface UsersServiceClient {
    @GetMapping("/v1/users/me")
    fun getMe(@RequestHeader("Authorization") token: String): UserBO
}