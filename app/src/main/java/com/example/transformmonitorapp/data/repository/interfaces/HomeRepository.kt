package com.example.transformmonitorapp.data.repository.interfaces

import com.example.transformmonitorapp.domain.dto.response.MessageResponse
import retrofit2.Response

interface HomeRepository {
    suspend fun getHome(token : String): Response<MessageResponse>
}