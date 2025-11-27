package com.example.transformmonitorapp.data.repository.impl

import android.content.Context
import com.example.transformmonitorapp.data.network.ApiServiceProvider
import com.example.transformmonitorapp.data.repository.interfaces.CreatorRepository
import com.example.transformmonitorapp.domain.dto.UserDto
import com.example.transformmonitorapp.domain.dto.response.MessageResponse
import com.example.transformmonitorapp.domain.model.Role

class CreatorRepositoryImpl(
    context: Context,
    private val token: String
) : CreatorRepository {
    private val api = ApiServiceProvider.creatorApi(context)

    override suspend fun getUsers(): List<UserDto> =
        api.getAllUsers("Bearer $token")

    override suspend fun getUsersByRole(role: String): List<UserDto> =
        api.getUsersByRole(role, "Bearer $token")

    override suspend fun getUserByEmail(email: String): UserDto? =
        api.getUserByEmail(email, "Bearer $token")

    override suspend fun changeUserRole(id: Long, role: Role): MessageResponse =
        api.changeUserRole(id, role, "Bearer $token")
}