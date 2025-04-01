@file:Suppress("UNCHECKED_CAST")

package com.yooshyasha.videopublishservice.security

import com.yooshyasha.videopublishservice.bo.UserBO
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
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class JwtUtilTest {
    @Mock
    private lateinit var jwtParser: JwtParser

    @InjectMocks
    private lateinit var jwtUtil: JwtUtil

    @BeforeEach
    fun setUp() {
        val secretKeyStringField = jwtUtil::class.java.getDeclaredField("SECRET")
        secretKeyStringField.isAccessible = true
        secretKeyStringField.set(jwtUtil, "secretKey")
    }

    @Test
    fun extractAllClaimsTestSuccess() {
        val user = UserBO(id = UUID.randomUUID(), name = "yooshyasha")

        val userDetails = Mockito.mock(UserDetails::class.java)
        val claims = Mockito.mock(Claims::class.java)
        val jws = Mockito.mock(Jws::class.java) as Jws<Claims>

        Mockito.`when`(userDetails.username).thenReturn(user.name)
        Mockito.`when`(claims.subject).thenReturn(user.name)
        Mockito.`when`(jws.payload).thenReturn(claims)

        Mockito.`when`(jwtParser.parseSignedClaims("jwtToken")).thenReturn(jws)

        val result = jwtUtil.extractAllClaims("jwtToken")

        assertEquals(userDetails.username, result.subject)
    }

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

        val result = jwtUtil.verify("jwtToken", userDetails)

        assertTrue(result)
    }
}