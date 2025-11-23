package com.example.transformmonitorapp.domain.dto

import com.example.transformmonitorapp.domain.model.Role

data class UserDto(
    val id: Long,
    val nameUKR: String,
    val email: String,
    val role: Role
)