package com.yooshyasha.videopublishservice.services

import com.yooshyasha.videopublishservice.bo.UserBO
import com.yooshyasha.videopublishservice.feign.UserServiceClient
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class UsersServiceTest {
    @Mock
    private lateinit var userServiceClient: UserServiceClient

    @InjectMocks
    private lateinit var usersService: UsersService

    @Test
    fun getMeTestSuccess() {
        val user = UserBO(id = UUID.randomUUID(), name = "yooshyasha")

        Mockito.`when`(userServiceClient.getMe("jwtToken")).thenReturn(user)

        val result = usersService.getMe("jwtToken")

        assertEquals(user, result
        )
    }
}