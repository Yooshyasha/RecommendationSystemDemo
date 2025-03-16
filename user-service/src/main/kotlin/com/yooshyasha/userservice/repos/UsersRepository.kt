package com.yooshyasha.userservice.repos

import com.yooshyasha.userservice.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsersRepository : JpaRepository<User, UUID> {
    fun findByName(name: String): User?

    fun existsByName(name: String): Boolean
}