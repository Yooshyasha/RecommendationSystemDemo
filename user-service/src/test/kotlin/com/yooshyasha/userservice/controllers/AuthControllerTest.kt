package com.yooshyasha.userservice.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.yooshyasha.userservice.dto.controllers.RequestLogin
import com.yooshyasha.userservice.dto.controllers.RequestRegister
import com.yooshyasha.userservice.services.AuthService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ExtendWith(MockitoExtension::class)
class AuthControllerTest {
    private lateinit var mockMvc: MockMvc

    @Mock
    private lateinit var authService: AuthService

    @InjectMocks
    private lateinit var authController: AuthController

    private val objectMapper = jacksonObjectMapper()

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build()
    }

    @Test
    fun login() {
        val request = RequestLogin(username = "yooshyasha", password = "passwd")

        Mockito.`when`(authService.login("yooshyasha", "passwd")).thenReturn("jwtToken")

        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.jwtToken").value("jwtToken"))
    }

    @Test
    fun register() {
        val request = RequestRegister(username = "yooshyasha", password = "passwd")

        Mockito.`when`(authService.register("yooshyasha", "passwd")).thenReturn("jwtToken")

        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.jwtToken").value("jwtToken"))
    }
}