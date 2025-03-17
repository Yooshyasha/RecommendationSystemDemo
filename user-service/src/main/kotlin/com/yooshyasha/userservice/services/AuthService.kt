package com.yooshyasha.userservice.services

import com.yooshyasha.userservice.entities.User
import com.yooshyasha.userservice.security.JwtUtil
import jakarta.security.auth.message.AuthException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val usersService: UsersService,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtUtil: JwtUtil,
) {
    fun login(username: String, password: String): String {
        val user = usersService.getUserByUsername(username)
            ?: throw UsernameNotFoundException("User not found")

        if (!passwordEncoder.matches(password, user.hashedPassword)) throw AuthException("Wrong password")

        return jwtUtil.generateToken(user)
    }

    fun register(username: String, password: String): String {
        val user = User(name = username, hashedPassword = passwordEncoder.encode(password))

        usersService.save(user)

        return jwtUtil.generateToken(user)
    }
}