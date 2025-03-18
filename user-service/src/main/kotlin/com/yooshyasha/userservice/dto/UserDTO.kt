package com.yooshyasha.userservice.dto

import com.yooshyasha.userservice.entities.User

data class UserDTO(
    val name: String,
    val email: String? = "",
) {
    constructor(user: User) : this(name = user.username, email = user.email)
}
