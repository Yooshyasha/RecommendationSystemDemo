package com.yooshyasha.userservice.services

import com.yooshyasha.userservice.entities.User
import com.yooshyasha.userservice.repos.UsersRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.util.*

@ExtendWith(MockitoExtension::class)
class UsersServiceTest {
    @Mock
    private lateinit var usersRepository: UsersRepository

    @InjectMocks
    private lateinit var usersService: UsersService

    @Test
    fun getUserByIdTestSuccess() {
        val id = UUID.randomUUID()

        val user = User(id = id)

        Mockito.`when`(usersRepository.findById(id)).thenReturn(Optional.of(user))

        val result = usersService.getUserById(id)

        assertEquals(user, result)
    }

    @Test
    fun getUserByIdTestFailedNotFound() {
        val id = UUID.randomUUID()

        Mockito.`when`(usersRepository.findById(id)).thenReturn(Optional.empty())

        val result = usersService.getUserById(id)

        assertEquals(null, result)
    }

    @Test
    fun getUserByUsernameSuccess() {
        val username = "yooshyasha"

        val user = User(name = username)

        Mockito.`when`(usersRepository.findByName(username)).thenReturn(user)

        val result = usersService.getUserByUsername(username)

        assertEquals(user, result)
    }

    @Test
    fun getUserByUsernameFailedNotFound() {
        val username = "yooshyasha"

        Mockito.`when`(usersRepository.findByName(username)).thenReturn(null)

        val result = usersService.getUserByUsername(username)

        assertEquals(null, result)
    }

    @Test
    fun usernameExistsTrue() {
        val username = "yooshyasha"

        Mockito.`when`(usersRepository.existsByName(username)).thenReturn(true)

        val result = usersService.usernameExists(username)

        assertEquals(true, result)
    }

    @Test
    fun usernameExistsFalse() {
        val username = "yooshyasha"

        Mockito.`when`(usersRepository.existsByName(username)).thenReturn(false)

        val result = usersService.usernameExists(username)

        assertEquals(false, result)
    }

    @Test
    fun save() {
        val user = User(id = UUID.randomUUID())

        Mockito.`when`(usersRepository.save(user)).thenReturn(user)

        val result = usersService.save(user)

        assertEquals(user, result)
    }

    @Test
    fun update() {
        val user = User(id = UUID.randomUUID())

        Mockito.`when`(usersRepository.save(user)).thenReturn(user)

        val result = usersService.update(user)

        assertEquals(user, result)
    }

    @Test
    fun getMeSuccess() {
        val username = "yooshyasha"

        val user = User(name = username)

        val auth = Mockito.mock(Authentication::class.java)
        Mockito.`when`(auth.name).thenReturn(username)

        val securityContext = Mockito.mock(SecurityContext::class.java)
        Mockito.`when`(securityContext.authentication).thenReturn(auth)
        SecurityContextHolder.setContext(securityContext)

        Mockito.`when`(usersRepository.findByName(username)).thenReturn(user)

        val result = usersService.getMe()

        assertEquals(user, result)
    }

    @Test
    fun getMeFailedNotFound() {
        val username = "yooshyasha"

        val auth = Mockito.mock(Authentication::class.java)
        Mockito.`when`(auth.name).thenReturn(username)

        val securityContext = Mockito.mock(SecurityContext::class.java)
        Mockito.`when`(securityContext.authentication).thenReturn(auth)
        SecurityContextHolder.setContext(securityContext)

        Mockito.`when`(usersRepository.findByName(username)).thenReturn(null)

        assertThrows<UsernameNotFoundException>(({usersService.getMe()}))
    }
}