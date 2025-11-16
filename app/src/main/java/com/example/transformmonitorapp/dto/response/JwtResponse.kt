package com.example.transformmonitorapp.dto.response

import com.example.transformmonitorapp.dto.UserDto

data class JwtResponse(
    val token: String,
    val userDto : UserDto
)