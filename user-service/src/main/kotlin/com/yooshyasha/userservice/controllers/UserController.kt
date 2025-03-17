package com.yooshyasha.userservice.controllers

import com.yooshyasha.userservice.entities.User
import com.yooshyasha.userservice.services.UsersService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val usersService: UsersService,
) {
    @GetMapping("/me")
    fun getMe(): ResponseEntity<User> {
        return ResponseEntity.ok(usersService.getMe())
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: String): ResponseEntity<User?> {
        return ResponseEntity.ok(usersService.getUserById(UUID.fromString(userId)))
    }
}