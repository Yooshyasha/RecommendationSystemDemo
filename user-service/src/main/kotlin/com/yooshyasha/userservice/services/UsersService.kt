package com.yooshyasha.userservice.services

import com.yooshyasha.userservice.entities.User
import com.yooshyasha.userservice.repos.UsersRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsersService(
    private val usersRepository: UsersRepository,
) {
    fun getUserById(id: UUID): User? {
        return usersRepository.findById(id).orElse(null)
    }

    fun getUserByUsername(username: String): User? {
        return usersRepository.findByName(username)
    }

    fun usernameExists(username: String): Boolean {
        return usersRepository.existsByName(username)
    }

    fun save(user: User): User {
        return usersRepository.save(user)
    }

    fun update(user: User): User {
        return usersRepository.save(user)
    }
}