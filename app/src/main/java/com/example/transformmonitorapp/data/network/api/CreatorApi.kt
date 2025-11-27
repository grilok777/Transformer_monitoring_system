package com.example.transformmonitorapp.data.network.api

import com.example.transformmonitorapp.domain.dto.UserDto
import com.example.transformmonitorapp.domain.dto.response.MessageResponse
import com.example.transformmonitorapp.domain.model.Role
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CreatorApi {
    @GET("/api/creator/users")
    suspend fun getAllUsers(@Header("Authorization") token: String): List<UserDto>

    @GET("/api/creator/users/by-role")
    suspend fun getUsersByRole(
        @Query("role") role: String,
        @Header("Authorization") token: String
    ): List<UserDto>

    @GET("/api/creator/users/by-email")
    suspend fun getUserByEmail(
        @Query("email") email: String,
        @Header("Authorization") token: String
    ): UserDto?

    @PUT("/api/creator/users/{id}/role")
    suspend fun changeUserRole(
        @Path("id") id: Long,
        @Query("role") role: Role,
        @Header("Authorization") token: String
    ): MessageResponse
}
