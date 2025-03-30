package com.yooshyasha.videoservice.services

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtParser
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    private val jwtParser: JwtParser,
) {
    fun verify(token: String, userDetails: UserDetails): Boolean {
        val claims = extractAllClaims(token)

        return (claims.subject == userDetails.username && !claims.expiration.before(Date()))
    }

    fun extractAllClaims(token: String): Claims {
        return jwtParser
            .parseSignedClaims(token)
            .payload
    }
}