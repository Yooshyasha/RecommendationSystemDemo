package com.yooshyasha.userservice.controllers

import com.yooshyasha.userservice.dto.UserDTO
import com.yooshyasha.userservice.entities.User
import com.yooshyasha.userservice.services.UsersService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    fun getMe(): ResponseEntity<UserDTO> {
        try {
            val user = usersService.getMe()

            return ResponseEntity.ok(UserDTO(user))
        } catch (e: Exception) {
            LoggerFactory.getLogger(this.javaClass).error("Error", e)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: String): ResponseEntity<UserDTO> {
        try {
            val user = usersService.getUserById(UUID.fromString(userId))

            return ResponseEntity.ok(UserDTO(user!!))
        } catch (e: Exception) {
            LoggerFactory.getLogger(this.javaClass).error("Error occurred while getting user info", e)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @GetMapping("/name/{username}")
    fun getUserByUsername(@PathVariable username: String): ResponseEntity<UserDTO> {
        try {
            val user = usersService.getUserByUsername(username)

            return ResponseEntity.ok(UserDTO(user!!))
        } catch (e: Exception) {
            LoggerFactory.getLogger(this.javaClass).error("Error occurred while getting user info", e)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }
}