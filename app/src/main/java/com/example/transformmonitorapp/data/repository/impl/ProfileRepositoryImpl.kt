package com.example.transformmonitorapp.data.repository.impl

import android.content.Context
import com.example.transformmonitorapp.data.network.ApiServiceProvider
import com.example.transformmonitorapp.data.repository.interfaces.ProfileRepository
import com.example.transformmonitorapp.domain.dto.request.ChangeEmailRequest
import com.example.transformmonitorapp.domain.dto.request.ChangeNameRequest
import com.example.transformmonitorapp.domain.dto.request.ChangePasswordRequest

class ProfileRepositoryImpl(context: Context,
                            private val token: String) : ProfileRepository {
    private val api = ApiServiceProvider.profileApi(context)

    override suspend fun changeEmail(request: ChangeEmailRequest) {
        api.changeEmail("Bearer $token", request)
    }

    override suspend fun changePassword(request: ChangePasswordRequest) {
        api.changePassword("Bearer $token", request)
    }

    override suspend fun changeName(request: ChangeNameRequest) {
        api.changeName("Bearer $token", request)
    }

    override suspend fun getProfile(){
        api.getProfile("Bearer $token")
    }
}