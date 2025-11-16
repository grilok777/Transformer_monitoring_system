package com.example.transformmonitorapp.network.services

import com.example.transformmonitorapp.dto.response.MessageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeApi {
    @GET("/api/home")
    suspend fun getHome(@Header("Authorization") token : String) : Response<MessageResponse>
}