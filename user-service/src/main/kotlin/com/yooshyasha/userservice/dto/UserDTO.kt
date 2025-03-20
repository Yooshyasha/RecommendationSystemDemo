package com.yooshyasha.userservice.dto

import com.yooshyasha.userservice.entities.User
import java.util.UUID

data class UserDTO(
    val id: UUID,
    val name: String,
    val email: String? = "",
) {
    constructor(user: User) : this(id = user.id!!, name = user.username, email = user.email)
}
