package com.yooshyasha.recommendationservice.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil(

) {
    @Value("\${jwt.secret}")
    private lateinit var SECRET: String

    private fun secretKey(): SecretKey {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET))
    }

    fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(secretKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun verify(token: String, userDetails: UserDetails): Boolean {
        val claims = extractAllClaims(token)

        return (claims.subject == userDetails.username && !claims.expiration.before(Date()))
    }
}