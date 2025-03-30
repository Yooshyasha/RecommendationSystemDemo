package com.yooshyasha.userservice.controllers

import com.yooshyasha.userservice.dto.controllers.RequestLogin
import com.yooshyasha.userservice.dto.controllers.RequestRegister
import com.yooshyasha.userservice.dto.controllers.ResponseLogin
import com.yooshyasha.userservice.dto.controllers.ResponseRegister
import com.yooshyasha.userservice.services.AuthService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/login")
    fun login(@RequestBody loginData: RequestLogin): ResponseEntity<ResponseLogin> {
        try {
            val result = authService.login(loginData.username, loginData.password)

            val response = ResponseLogin(result)

            return ResponseEntity.ok(response)
        } catch (e: Exception) {
            LoggerFactory.getLogger(this.javaClass).error("Exception occurred", e)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody registerData: RequestRegister): ResponseEntity<ResponseRegister> {
        try {
            val result = authService.register(registerData.username, registerData.password)

            val response = ResponseRegister(result)

            return ResponseEntity.ok(response)
        } catch (e: Exception) {
            LoggerFactory.getLogger(this.javaClass).error("Exception occurred", e)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }
}