package com.example.transformmonitorapp.network.services

import com.example.transformmonitorapp.dto.UserDto
import com.example.transformmonitorapp.dto.response.MessageResponse
import com.example.transformmonitorapp.model.Role
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiServiceCreator {
    @GET("/api/creator/users")
    suspend fun getAllUsers(@Header("Authorization") token: String): List<UserDto>

    @GET("/api/users/by-role")
    suspend fun getUsersByRole(@Query("role") role: String): List<UserDto>

    @GET("/api/users/by-role")
    suspend fun getUserByEmail(@Query("email") email: String): UserDto?

    @PUT("/api/users/{id}/role")
    suspend fun changeUserRole(
        @Part("id") id: Long,
        @Query("role") role: Role
    ): MessageResponse
}