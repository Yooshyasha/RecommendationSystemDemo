package com.yooshyasha.recommendationservice.security

import com.yooshyasha.recommendationservice.services.UsersService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val usersService: UsersService,
    private val jwtUtil: JwtUtil,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return filterChain.doFilter(request, response)
        }

        val token = authHeader.substring(7)

        try {
            val user = usersService.getMe(authHeader)

            if (jwtUtil.verify(token, user)) {
                val auth = UsernamePasswordAuthenticationToken(user, null, user.authorities)

                auth.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = auth
            }
        } catch (e: Exception) {
            LoggerFactory.getLogger(this.javaClass).error(e.message, e)
        }

        return filterChain.doFilter(request, response)
    }
}