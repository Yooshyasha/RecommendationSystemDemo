package com.yooshyasha.userservice.security

import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import javax.crypto.SecretKey

@Component
class JwtParserConfig(
    private val secretKey: SecretKey,
) {
    @Bean
    fun jwtParser(): JwtParser {
        return Jwts.parser().verifyWith(secretKey).build()
    }
}