package com.example.transformmonitorapp.data.network.api

import com.example.transformmonitorapp.domain.dto.request.LoginRequest
import com.example.transformmonitorapp.domain.dto.request.LogoutRequest
import com.example.transformmonitorapp.domain.dto.request.RefreshTokenRequest
import com.example.transformmonitorapp.domain.dto.request.RegisterRequest
import com.example.transformmonitorapp.domain.dto.response.JwtResponse
import com.example.transformmonitorapp.domain.dto.response.MessageResponse
import com.example.transformmonitorapp.domain.dto.response.RefreshTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<MessageResponse>

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<JwtResponse>

    @POST("/api/auth/logout")
    suspend fun logout(@Body request: LogoutRequest): Response<MessageResponse>

    @GET("api/auth/ping")
    suspend fun ping(): Response<Unit>

    @POST("/api/auth/refresh")
    suspend fun refreshToken(@Body req: RefreshTokenRequest): Response<RefreshTokenResponse>
}