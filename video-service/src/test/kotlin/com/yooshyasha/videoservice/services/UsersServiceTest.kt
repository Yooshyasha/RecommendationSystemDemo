package com.yooshyasha.videoservice.services

import com.yooshyasha.videoservice.bo.UserBO
import com.yooshyasha.videoservice.feign.UsersServiceClient
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class UsersServiceTest {
    @Mock
    private lateinit var usersServiceClient: UsersServiceClient

    @InjectMocks
    private lateinit var usersService: UsersService

    @Test
    fun getMe() {
        val user = UserBO(id = UUID.randomUUID(), "yooshyasha")

        Mockito.`when`(usersServiceClient.getMe("jwtToken")).thenReturn(user)

        val result = usersService.getMe("jwtToken")

        assertEquals(user, result)
    }

    @Test
    fun getUserByUsername() {
        val user = UserBO(id = UUID.randomUUID(), "yooshyasha")

        Mockito.`when`(usersServiceClient.getUserByUsername(user.name)).thenReturn(user)

        val result = usersService.getUserByUsername(user.name)

        assertEquals(user, result)
    }
}