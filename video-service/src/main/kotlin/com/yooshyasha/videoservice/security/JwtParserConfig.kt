package com.yooshyasha.videoservice.security

import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*
import javax.crypto.SecretKey

@Configuration
class JwtParserConfig {
    @Value("\${jwt.secret}")
    private lateinit var SECRET: String

    @Bean
    fun jwtParser(secretKey: SecretKey): JwtParser {
        return Jwts.parser().verifyWith(secretKey).build()
    }

    @Bean
    fun secretKey(): SecretKey {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET))
    }
}