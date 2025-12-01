package com.example.transformmonitorapp.data.repository.interfaces

import com.example.transformmonitorapp.domain.dto.request.ChangeEmailRequest
import com.example.transformmonitorapp.domain.dto.request.ChangeNameRequest
import com.example.transformmonitorapp.domain.dto.request.ChangePasswordRequest

interface ProfileRepository {

    suspend fun changeEmail(request: ChangeEmailRequest)

    suspend fun changePassword(request: ChangePasswordRequest)

    suspend fun changeName(request: ChangeNameRequest)

    suspend fun getProfile()
}