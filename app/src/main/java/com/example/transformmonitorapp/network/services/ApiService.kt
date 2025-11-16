package com.example.transformmonitorapp.network.services

import com.example.transformmonitorapp.dto.UserDto
import com.example.transformmonitorapp.dto.request.LoginRequest
import com.example.transformmonitorapp.dto.request.LogoutRequest
import com.example.transformmonitorapp.dto.request.RegisterRequest
import com.example.transformmonitorapp.dto.response.JwtResponse
import com.example.transformmonitorapp.dto.response.MessageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<MessageResponse>

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<JwtResponse>

    @POST("/api/auth/logout")
    suspend fun logout(@Body request: LogoutRequest): Response<MessageResponse>
    @GET("ping")
    suspend fun ping(): Response<Unit>
    @GET("/api/users")
    suspend fun getAllUsers(string: String) : List<UserDto>
}