package com.yooshyasha.userservice.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity
data class User(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    val name: String = "",

    val email: String = "",

    val hashedPassword: String = "",
) : UserDetails {
    override fun getUsername(): String {
        return name
    }

    override fun getPassword(): String {
        return hashedPassword
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }
}
