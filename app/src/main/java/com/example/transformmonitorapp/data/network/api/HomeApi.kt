package com.example.transformmonitorapp.data.network.api

import com.example.transformmonitorapp.domain.dto.response.MessageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeApi {
    @GET("/api/home")
    suspend fun getHome(@Header("Authorization") token : String) : Response<MessageResponse>
}