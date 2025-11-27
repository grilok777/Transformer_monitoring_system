package com.example.transformmonitorapp.domain.dto.response

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String
)