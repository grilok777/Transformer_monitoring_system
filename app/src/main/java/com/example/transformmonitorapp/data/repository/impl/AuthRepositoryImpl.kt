package com.example.transformmonitorapp.data.repository.impl
import android.content.Context
import com.example.transformmonitorapp.data.network.ApiServiceProvider
import com.example.transformmonitorapp.data.repository.interfaces.AuthRepository
import com.example.transformmonitorapp.domain.dto.request.LoginRequest
import com.example.transformmonitorapp.domain.dto.request.LogoutRequest
import com.example.transformmonitorapp.domain.dto.request.RegisterRequest
import com.example.transformmonitorapp.domain.dto.response.JwtResponse
import com.example.transformmonitorapp.domain.dto.response.MessageResponse
import retrofit2.Response

class AuthRepositoryImpl(context: Context) : AuthRepository {
    private val api = ApiServiceProvider.authApi(context)

    override suspend fun registerUser(request: RegisterRequest): Response<MessageResponse> =
        api.register(request)

    override suspend fun loginUser(request: LoginRequest): Response<JwtResponse> =
        api.login(request)

    override suspend fun logout(token: LogoutRequest): Response<MessageResponse> {
        return api.logout(token)
    }

    override suspend fun pingUser(): Response<Unit> =
        api.ping()
}