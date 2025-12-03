package com.example.transformmonitorapp.data.network.api

import com.example.transformmonitorapp.domain.dto.UserDto
import com.example.transformmonitorapp.domain.dto.request.ChangeEmailRequest
import com.example.transformmonitorapp.domain.dto.request.ChangeNameRequest
import com.example.transformmonitorapp.domain.dto.request.ChangePasswordRequest
import com.example.transformmonitorapp.domain.dto.response.MessageResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface ProfileApi {

    @GET("/api/profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): UserDto

    @PUT("/api/profile/name")
    suspend fun changeName(
        @Header("Authorization") token: String,
        @Body request: ChangeNameRequest
    ): MessageResponse

    @PUT("/api/profile/password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body request: ChangePasswordRequest
    ): MessageResponse

    @PUT("/api/profile/email")
    suspend fun changeEmail(
        @Header("Authorization") token: String,
        @Body request: ChangeEmailRequest
    ): MessageResponse
}