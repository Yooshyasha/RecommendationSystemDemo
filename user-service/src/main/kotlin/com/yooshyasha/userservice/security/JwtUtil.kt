package com.yooshyasha.userservice.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil(
    private val jwtParser: JwtParser,
    private val secretKey: SecretKey,
) {
    @Value("\${jwt.secret}")
    private lateinit var SECRET: String

    private final val LIFETIME = 60 * 60 * 1000

    fun verify(token: String, userDetails: UserDetails): Boolean {
        val claims = extractAllClaimsFromToken(token)

        return (claims.subject == userDetails.username && !claims.expiration.before(Date()))
    }

    fun extractAllClaimsFromToken(token: String): Claims {
        return jwtParser
            .parseSignedClaims(token)
            .payload
    }

    fun extractUsernameFromToken(token: String): String? {
        return extractAllClaimsFromToken(token).subject
    }

    fun generateToken(userDetails: UserDetails): String {
        val now = Date()
        val expiredAt = Date(now.time + LIFETIME)

        return Jwts.builder()
            .signWith(secretKey)
            .issuedAt(now)
            .expiration(expiredAt)
            .subject(userDetails.username)
            .compact()
    }

    @Bean
    fun secretKey(): SecretKey {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET))
    }
}