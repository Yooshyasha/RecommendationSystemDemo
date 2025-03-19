package com.yooshyasha.videoservice.bo

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

data class UserBO(
    val id: UUID,
    val name: String,
    val email: String? = null,
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