package com.yooshyasha.videopublishservice.feign

import com.yooshyasha.videopublishservice.bo.UserBO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient("user-service")
interface UserServiceClient {
    @GetMapping("/v1/users/me")
    fun getMe(@RequestHeader("Authorization") token: String) : UserBO
}