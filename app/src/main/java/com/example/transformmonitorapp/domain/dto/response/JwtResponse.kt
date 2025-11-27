package com.example.transformmonitorapp.domain.dto.response

import com.example.transformmonitorapp.domain.dto.UserDto

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String,
    val userDto : UserDto
)