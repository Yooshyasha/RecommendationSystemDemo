package com.yooshyasha.userservice.security

import com.yooshyasha.userservice.services.UsersService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtUtil: JwtUtil,
    private val usersService: UsersService,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)

            val username = jwtUtil.extractUsernameFromToken(token)
                ?: return filterChain.doFilter(request, response)

            val user = usersService.getUserByUsername(username)
                ?: return filterChain.doFilter(request, response)

            if (jwtUtil.verify(token, user)) {
                val auth = UsernamePasswordAuthenticationToken(user, null, user.authorities)

                auth.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = auth
            }
        }

        return filterChain.doFilter(request, response)
    }
}