package com.example.transformmonitorapp.data.repository.interfaces

import com.example.transformmonitorapp.domain.dto.UserDto
import com.example.transformmonitorapp.domain.dto.response.MessageResponse
import com.example.transformmonitorapp.domain.model.Role

interface CreatorRepository{
    suspend fun getUsers(): List<UserDto>

    suspend fun getUsersByRole(role: String): List<UserDto>

    suspend fun getUserByEmail(email: String): UserDto?

    suspend fun changeUserRole(id: Long, role: Role): MessageResponse
}