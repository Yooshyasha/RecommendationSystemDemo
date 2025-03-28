package com.yooshyasha.userservice.services

import com.yooshyasha.userservice.entities.User
import com.yooshyasha.userservice.security.JwtUtil
import jakarta.security.auth.message.AuthException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.text.Typography.times

@ExtendWith(MockitoExtension::class)
class AuthServiceTest {
    @Mock
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    @Mock
    private lateinit var usersService: UsersService

    @Mock
    private lateinit var jwtUtil: JwtUtil

    @InjectMocks
    private lateinit var authService: AuthService

    @Test
    fun loginTestSuccess() {
        val user = User(name = "yooshyasha", hashedPassword = "hashedPassword")

        Mockito.`when`(usersService.getUserByUsername("yooshyasha")).thenReturn(user)
        Mockito.`when`(passwordEncoder.matches("password", "hashedPassword")).thenReturn(true)
        Mockito.`when`(jwtUtil.generateToken(user)).thenReturn("jwtToken")

        val token = authService.login("yooshyasha", "password")

        assertEquals("jwtToken", token)
    }

    @Test
    fun loginTestFailedPasswordMatches() {
        val user = User(name = "yooshyasha", hashedPassword = "hashedPassword")

        Mockito.`when`(usersService.getUserByUsername("yooshyasha")).thenReturn(user)
        Mockito.`when`(passwordEncoder.matches("passwd", "hashedPassword")).thenReturn(false)

        assertThrows(AuthException::class.java, ({authService.login("yooshyasha", "passwd")}))
    }

    @Test
    fun loginTestFailedUsernameNotFound() {
        Mockito.`when`(usersService.getUserByUsername("yooshyasha")).thenReturn(null)

        assertThrows(UsernameNotFoundException::class.java, ({authService.login("yooshyasha", "passwd")}))

    }

    @Test
    fun registerTestSuccess() {
        val user = User(name = "yooshyasha", hashedPassword = "hashedPassword")

        Mockito.`when`(jwtUtil.generateToken(user)).thenReturn("jwtToken")
        Mockito.`when`(passwordEncoder.encode("passwd")).thenReturn("hashedPassword")

        val token = authService.register("yooshyasha", "passwd")

        assertEquals("jwtToken", token)
    }
}