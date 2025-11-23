package com.example.transformmonitorapp.data.repository.impl
import com.example.transformmonitorapp.data.network.ApiServiceProvider.authApi
import com.example.transformmonitorapp.data.repository.interfaces.AuthRepository
import com.example.transformmonitorapp.domain.dto.request.LoginRequest
import com.example.transformmonitorapp.domain.dto.request.LogoutRequest
import com.example.transformmonitorapp.domain.dto.request.RegisterRequest
import com.example.transformmonitorapp.domain.dto.response.JwtResponse
import com.example.transformmonitorapp.domain.dto.response.MessageResponse
import retrofit2.Response

class AuthRepositoryImpl : AuthRepository {
    override suspend fun registerUser(request: RegisterRequest): Response<MessageResponse> =
        authApi.register(request)

    override suspend fun loginUser(request: LoginRequest): Response<JwtResponse> =
        authApi.login(request)

    override suspend fun logout(token: LogoutRequest): Response<MessageResponse> {
        return authApi.logout(token)
    }

    override suspend fun pingUser(): Response<Unit> =
        authApi.ping()
}