package com.yooshyasha.userservice.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtParser
import org.junit.jupiter.api.BeforeEach
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
import javax.crypto.SecretKey
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class JwtUtilTest {
    @Mock
    private lateinit var jwtParser: JwtParser

    @Mock
    private lateinit var secretKey: SecretKey

    @InjectMocks
    private lateinit var jwtUtil: JwtUtil

    @BeforeEach
    fun setUp() {
        val secretField = JwtUtil::class.java.getDeclaredField("SECRET")

        secretField.isAccessible = true

        secretField.set(jwtUtil, "secretKey")
    }


    @Test
    fun verifyTestSuccess() {
        val token = "jwtToken"
        val username = "yooshyasha"

        val claims = Mockito.mock(Claims::class.java)
        val userDetails = Mockito.mock(UserDetails::class.java)
        @Suppress("UNCHECKED_CAST") val jws = Mockito.mock(Jws::class.java) as Jws<Claims>

        Mockito.`when`(jws.payload).thenReturn(claims)

        Mockito.`when`(claims.subject).thenReturn(username)
        Mockito.`when`(claims.expiration).thenReturn(Date(System.currentTimeMillis() + 10000))
        Mockito.`when`(jwtParser.parseSignedClaims(token)).thenReturn(jws)
        Mockito.`when`(jwtUtil.extractAllClaimsFromToken(token)).thenReturn(claims)

        Mockito.`when`(userDetails.username).thenReturn(username)

        val result = jwtUtil.verify(token, userDetails)

        assertTrue(result)
    }

    @Test
    fun verifyTestFailedExpiration() {
        val token = "jwtToken"
        val username = "yooshyasha"

        val claims = Mockito.mock(Claims::class.java)
        val userDetails = Mockito.mock(UserDetails::class.java)
        @Suppress("UNCHECKED_CAST") val jws = Mockito.mock(Jws::class.java) as Jws<Claims>

        Mockito.`when`(jws.payload).thenReturn(claims)

        Mockito.`when`(claims.subject).thenReturn(username)
        Mockito.`when`(claims.expiration).thenReturn(Date(System.currentTimeMillis() - 10000))
        Mockito.`when`(jwtParser.parseSignedClaims(token)).thenReturn(jws)
        Mockito.`when`(jwtUtil.extractAllClaimsFromToken(token)).thenReturn(claims)

        Mockito.`when`(userDetails.username).thenReturn(username)

        val result = jwtUtil.verify(token, userDetails)

        assertFalse(result)
    }

    @Test
    fun verifyTestFailedUsername() {
        val token = "jwtToken"
        val username = "yooshyasha"

        val claims = Mockito.mock(Claims::class.java)
        val userDetails = Mockito.mock(UserDetails::class.java)
        @Suppress("UNCHECKED_CAST") val jws = Mockito.mock(Jws::class.java) as Jws<Claims>

        Mockito.`when`(jws.payload).thenReturn(claims)

        Mockito.`when`(claims.subject).thenReturn("not-$username")
        Mockito.`when`(claims.expiration).thenReturn(Date(System.currentTimeMillis() + 15000))
        Mockito.`when`(jwtParser.parseSignedClaims(token)).thenReturn(jws)
        Mockito.`when`(jwtUtil.extractAllClaimsFromToken(token)).thenReturn(claims)

        Mockito.`when`(userDetails.username).thenReturn(username)

        val result = jwtUtil.verify(token, userDetails)

        assertFalse(result)
    }

    @Test
    fun extractAllClaimsFromTokenTestSuccess() {
        val token = "jwtToken"
        val username = "yooshyasha"

        val claims = Mockito.mock(Claims::class.java)
        @Suppress("UNCHECKED_CAST") val jws = Mockito.mock(Jws::class.java) as Jws<Claims>

        Mockito.`when`(jws.payload).thenReturn(claims)

        Mockito.`when`(claims.subject).thenReturn(username)
        Mockito.`when`(claims.expiration).thenReturn(Date(System.currentTimeMillis() + 15000))
        Mockito.`when`(jwtParser.parseSignedClaims(token)).thenReturn(jws)

        val result = jwtUtil.extractAllClaimsFromToken(token)

        assertEquals(claims, result)
    }

    @Test
    fun extractUsernameFromTokenTestSuccess() {
        val token = "jwtToken"
        val username = "yooshyasha"

        val claims = Mockito.mock(Claims::class.java)
        @Suppress("UNCHECKED_CAST") val jws = Mockito.mock(Jws::class.java) as Jws<Claims>

        Mockito.`when`(jws.payload).thenReturn(claims)

        Mockito.`when`(claims.subject).thenReturn(username)
        Mockito.`when`(claims.expiration).thenReturn(Date(System.currentTimeMillis() + 15000))
        Mockito.`when`(jwtParser.parseSignedClaims(token)).thenReturn(jws)

        val result = jwtUtil.extractUsernameFromToken(token)

        assertEquals(username, result)
    }
}