package com.example.transformmonitorapp.network.services

import com.example.transformmonitorapp.dto.response.MessageResponse
import retrofit2.http.GET

interface ApiServiceOperator {
    @GET("/api/home")
    suspend fun func() : MessageResponse
}