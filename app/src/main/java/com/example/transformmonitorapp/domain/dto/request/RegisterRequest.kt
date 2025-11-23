package com.example.transformmonitorapp.domain.dto.request

data class RegisterRequest(
    val email: String,
    val password: String,
    val nameUKR: String,
)