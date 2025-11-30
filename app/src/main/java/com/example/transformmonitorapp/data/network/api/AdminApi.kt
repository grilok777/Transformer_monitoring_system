package com.example.transformmonitorapp.data.network.api

import com.example.transformmonitorapp.domain.dto.AlertDto
import com.example.transformmonitorapp.domain.dto.TransformerDto
import com.example.transformmonitorapp.domain.dto.request.TransformerRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AdminApi {
    @POST("/api/admin/transformer/create")
    suspend fun createTransformer(@Body request: TransformerRequest,
           @Header("Authorization") token: String): Response<TransformerDto>


    @PUT("/api/admin/transformer/update/{id}")
    suspend fun updateTransformer(
        @Path("id") id: Long,
        @Body request: TransformerRequest,
        @Header("Authorization") token: String
    ): Response<TransformerDto>

    @DELETE("/api/admin/transformer/deactivate/{id}")
    suspend fun deactivateTransformer(
        @Path("id") id: Long,
        @Header("Authorization") token: String
    ): Response<Void>

    @GET("/api/admin/transformer/export/{id}")
    suspend fun exportTransformer(
        @Path("id") id: Long,
        @Header("Authorization") token: String
    ): Response<TransformerDto>

    @GET("/api/admin/transformers/export/{from}/{to}")
    suspend fun exportTransformerRange(
        @Path("from") from: Long,
        @Path("to") to: Long,
        @Header("Authorization") token: String
    ): Response<List<TransformerDto>>

    @GET("/api/admin/transformers/export/all")
    suspend fun exportAllTransformers(@Header("Authorization") token: String
    ): Response<List<TransformerDto>>

    @GET("/api/admin/alerts")
    suspend fun getAllAlerts(@Header("Authorization") token: String): Response<List<AlertDto>>

    @GET("/api/admin/alerts/critical")
    suspend fun getCriticalAlerts(@Header("Authorization") token: String): Response<List<AlertDto>>

    @GET("/api/admin/logs/export/{id}")
    suspend fun exportLogs(
        @Path("id") id: Long,
        @Header("Authorization") token: String
    ): Response<List<String>>
}