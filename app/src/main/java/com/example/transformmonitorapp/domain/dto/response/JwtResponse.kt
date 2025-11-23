package com.example.transformmonitorapp.domain.dto.response

import com.example.transformmonitorapp.domain.dto.UserDto

data class JwtResponse(
    val token: String,
    val userDto : UserDto
)