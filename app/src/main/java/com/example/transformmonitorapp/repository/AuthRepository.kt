package com.example.transformmonitorapp.repository

import com.example.transformmonitorapp.dto.request.RegisterRequest
import com.example.transformmonitorapp.network.RetrofitInstance

class AuthRepository {
    suspend fun registerUser(request: RegisterRequest) =
        RetrofitInstance.authApi.register(request)
}