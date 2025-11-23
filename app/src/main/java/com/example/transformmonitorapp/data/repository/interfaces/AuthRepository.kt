package com.example.transformmonitorapp.data.repository.interfaces

import com.example.transformmonitorapp.domain.dto.request.LoginRequest
import com.example.transformmonitorapp.domain.dto.request.LogoutRequest
import com.example.transformmonitorapp.domain.dto.request.RegisterRequest
import com.example.transformmonitorapp.domain.dto.response.JwtResponse
import com.example.transformmonitorapp.domain.dto.response.MessageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AuthRepository {
    suspend fun registerUser(request: RegisterRequest): Response<MessageResponse>

    suspend fun loginUser(request: LoginRequest): Response<JwtResponse>

    suspend fun logout(token: LogoutRequest): Response<MessageResponse>

    suspend fun pingUser(): Response<Unit>
}