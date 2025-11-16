package com.example.transformmonitorapp.model

class User(
    val id: Long,
    val nameUKR: String,
    val email: String,
    private var password: String,
    val role: Role
)