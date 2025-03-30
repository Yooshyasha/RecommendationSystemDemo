package com.yooshyasha.videoservice.services

import com.yooshyasha.videoservice.bo.UserBO
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtParser
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.quality.Strictness
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthServiceTest {
    @Mock
    private lateinit var jwtParser: JwtParser

    @InjectMocks
    private lateinit var authService: AuthService

    @Test
    fun verifyTestSuccess() {
        val user = UserBO(id = UUID.randomUUID(), name = "yooshyasha")

        val userDetails = Mockito.mock(UserDetails::class.java)
        val claims = Mockito.mock(Claims::class.java)
        val jws = Mockito.mock(Jws::class.java) as Jws<Claims>

        Mockito.`when`(userDetails.username).thenReturn(user.name)
        Mockito.`when`(claims.subject).thenReturn(user.name)
        Mockito.`when`(claims.expiration).thenReturn(Date(System.currentTimeMillis() + 10000))
        Mockito.`when`(jws.payload).thenReturn(claims)
        Mockito.`when`(jwtParser.parseSignedClaims("jwtToken")).thenReturn(jws)

        val result = authService.verify("jwtToken", userDetails)

        assertTrue(result)
    }

    @Test
    fun verifyTestFailedUsername() {
        val user = UserBO(id = UUID.randomUUID(), name = "yooshyasha")

        val userDetails = Mockito.mock(UserDetails::class.java)
        val claims = Mockito.mock(Claims::class.java)
        @Suppress("UNCHECKED_CAST") val jws = Mockito.mock(Jws::class.java) as Jws<Claims>

        Mockito.`when`(userDetails.username).thenReturn(user.name)
        Mockito.`when`(claims.subject).thenReturn("not-${user.name}")
        Mockito.`when`(claims.expiration).thenReturn(Date(System.currentTimeMillis() + 10000))
        Mockito.`when`(jws.payload).thenReturn(claims)
        Mockito.`when`(jwtParser.parseSignedClaims("jwtToken")).thenReturn(jws)

        val result = authService.verify("jwtToken", userDetails)

        assertFalse(result)
    }

    @Test
    fun verifyTestFailedExpiration() {
        val user = UserBO(id = UUID.randomUUID(), name = "yooshyasha")

        val userDetails = Mockito.mock(UserDetails::class.java)
        val claims = Mockito.mock(Claims::class.java)
        @Suppress("UNCHECKED_CAST") val jws = Mockito.mock(Jws::class.java) as Jws<Claims>

        Mockito.`when`(userDetails.username).thenReturn(user.name)
        Mockito.`when`(claims.subject).thenReturn(user.name)
        Mockito.`when`(claims.expiration).thenReturn(Date(System.currentTimeMillis() - 10000))
        Mockito.`when`(jws.payload).thenReturn(claims)
        Mockito.`when`(jwtParser.parseSignedClaims("jwtToken")).thenReturn(jws)

        val result = authService.verify("jwtToken", userDetails)

        assertFalse(result)
    }

    @Test
    fun extractAllClaims() {
        val user = UserBO(id = UUID.randomUUID(), name = "yooshyasha")

        val claims = Mockito.mock(Claims::class.java)
        @Suppress("UNCHECKED_CAST") val jws = Mockito.mock(Jws::class.java) as Jws<Claims>

        Mockito.`when`(claims.subject).thenReturn(user.name)
        Mockito.`when`(claims.expiration).thenReturn(Date(System.currentTimeMillis() - 10000))
        Mockito.`when`(jws.payload).thenReturn(claims)
        Mockito.`when`(jwtParser.parseSignedClaims("jwtToken")).thenReturn(jws)

        val result = authService.extractAllClaims("jwtToken")

        assertEquals(user.name, result.subject)
    }
}