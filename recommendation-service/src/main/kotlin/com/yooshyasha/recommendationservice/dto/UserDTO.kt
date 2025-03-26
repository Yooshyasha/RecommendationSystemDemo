package com.yooshyasha.recommendationservice.dto

import java.util.*

data class UserDTO(
    val id: UUID,
    val name: String,
    val email: String? = "",
)