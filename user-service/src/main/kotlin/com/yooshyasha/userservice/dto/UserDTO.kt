package com.yooshyasha.userservice.dto

import com.yooshyasha.userservice.entities.User

data class UserDTO(
    val username: String,
    val email: String? = "",
) {
    constructor(user: User) : this(username = user.username, email = user.email)
}
