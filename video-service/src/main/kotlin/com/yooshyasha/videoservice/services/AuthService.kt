package com.yooshyasha.videoservice.services

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class AuthService {
    @Value("\${jwt.secret}")
    private lateinit var SECRET: String

    private fun secretKey(): SecretKey {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET))
    }

    fun verify(token: String, userDetails: UserDetails): Boolean {
        val claims = extractAllClaims(token)

        return (claims.subject == userDetails.username && !claims.expiration.before(Date()))
    }

    fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(secretKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }
}