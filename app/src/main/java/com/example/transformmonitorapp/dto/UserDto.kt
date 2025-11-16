package com.example.transformmonitorapp.dto

import com.example.transformmonitorapp.model.Role

data class UserDto(
    val id: Long,
    val nameUKR: String,
    val email: String,
    val role: Role
)