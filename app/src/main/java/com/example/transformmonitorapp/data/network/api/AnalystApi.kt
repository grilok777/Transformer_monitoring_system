package com.example.transformmonitorapp.data.network.api

import com.example.transformmonitorapp.domain.dto.AlertDto
import com.example.transformmonitorapp.domain.dto.TransformerDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface AnalystApi {
    @GET("/api/analyst/transformer/export/{id}")
    suspend fun exportTransformer(
        @Path("id") id: Long,
        @Header("Authorization") token: String
    ): Response<TransformerDto>

    @GET("/api/analyst/transformers/export/{from}/{to}")
    suspend fun exportTransformerRange(
        @Path("from") from: Long,
        @Path("to") to: Long,
        @Header("Authorization") token: String
    ): Response<List<TransformerDto>>

    @GET("/api/analyst/transformers/export/all")
    suspend fun exportAllTransformers(
        @Header("Authorization") token: String
    ): Response<List<TransformerDto>>

    @GET("/api/analyst/alerts")
    suspend fun getAllAlerts(@Header("Authorization") token: String): Response<List<AlertDto>>

    @GET("/api/analyst/alerts/critical")
    suspend fun getCriticalAlerts(@Header("Authorization") token: String): Response<List<AlertDto>>

    @GET("/api/analyst/logs/export/{id}")
    suspend fun exportLogs(
        @Path("id") id: Long,
        @Header("Authorization") token: String
    ): Response<List<String>>
}