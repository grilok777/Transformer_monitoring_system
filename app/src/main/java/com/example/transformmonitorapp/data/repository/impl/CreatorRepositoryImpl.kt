package com.example.transformmonitorapp.data.repository.impl

import com.example.transformmonitorapp.data.network.api.CreatorApi
import com.example.transformmonitorapp.data.repository.interfaces.CreatorRepository
import com.example.transformmonitorapp.domain.dto.UserDto
import com.example.transformmonitorapp.domain.dto.response.MessageResponse
import com.example.transformmonitorapp.domain.model.Role

class CreatorRepositoryImpl(private val creatorApi: CreatorApi) : CreatorRepository {
    override suspend fun getUsers(): List<UserDto> =
        creatorApi.getAllUsers()

    override suspend fun getUsersByRole(role: String): List<UserDto> =
        creatorApi.getUsersByRole(role)

    override suspend fun getUserByEmail(email: String): UserDto? =
        creatorApi.getUserByEmail(email)

    override suspend fun changeUserRole(id: Long, role: Role): MessageResponse =
        creatorApi.changeUserRole(id, role)
}