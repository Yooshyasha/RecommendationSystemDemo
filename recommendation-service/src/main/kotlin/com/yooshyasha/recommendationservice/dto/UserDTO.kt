package com.yooshyasha.recommendationservice.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

data class UserDTO(
    val id: UUID,
    val name: String,
    val email: String? = "",
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getPassword(): String {
        return ""
    }

    override fun getUsername(): String {
       return name
    }

}