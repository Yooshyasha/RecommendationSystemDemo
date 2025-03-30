package com.yooshyasha.userservice.controllers

import com.yooshyasha.userservice.entities.User
import com.yooshyasha.userservice.services.UsersService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*

@ExtendWith(MockitoExtension::class)
class UserControllerTest {
    @Mock
    private lateinit var usersService: UsersService

    @InjectMocks
    private lateinit var userController: UserController

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build()
    }

    @Test
    fun getMe() {
        val user = User(name = "yooshyasha")

        Mockito.`when`(usersService.getMe()).thenReturn(user)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/users/me")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.username").value(user.username))
    }

    @Test
    fun getUser() {
        val user = User(id = UUID.randomUUID(), name = "yooshyasha")

        Mockito.`when`(usersService.getUserById(user.id!!)).thenReturn(user)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/users/${user.id}")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.username").value(user.username))
    }
}