package com.example.transformmonitorapp.data.network.api

import com.example.transformmonitorapp.domain.dto.UserDto
import com.example.transformmonitorapp.domain.dto.response.MessageResponse
import com.example.transformmonitorapp.domain.model.Role
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface CreatorApi {
    @GET("/api/creator/users")
    suspend fun getAllUsers(): List<UserDto>

    @GET("/api/users/by-role")
    suspend fun getUsersByRole(@Query("role") role: String): List<UserDto>

    @GET("/api/users/by-email")
    suspend fun getUserByEmail(@Query("email") email: String): UserDto?

    @PUT("/api/users/{id}/role")
    suspend fun changeUserRole(
        @Part("id") id: Long,
        @Query("role") role: Role
    ): MessageResponse
}