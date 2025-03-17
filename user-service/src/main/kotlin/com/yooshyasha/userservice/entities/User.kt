package com.yooshyasha.userservice.entities

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity
@Table(name = "users")
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
