package com.example.transformmonitorapp.data.repository.impl

import android.content.Context
import com.example.transformmonitorapp.data.network.ApiServiceProvider
import com.example.transformmonitorapp.data.repository.interfaces.AnalyticRepository
import com.example.transformmonitorapp.domain.dto.AlertDto
import com.example.transformmonitorapp.domain.dto.TransformerDto

import retrofit2.Response

class AnalyticRepositoryImpl(context: Context, private val token: String): AnalyticRepository {

    private val api = ApiServiceProvider.analystApi(context)
    override suspend fun exportTransformer(id: Long): Response<TransformerDto> =
        api.exportTransformer(id, "Bearer $token")

    override suspend fun exportTransformerRange(from: Long, to: Long): Response<List<TransformerDto>> =
        api.exportTransformerRange(from, to,"Bearer $token")

    override suspend fun exportAllTransformers(): Response<List<TransformerDto>> =
        api.exportAllTransformers("Bearer $token")

    override suspend fun getAllAlerts(): Response<List<AlertDto>> =
        api.getAllAlerts("Bearer $token")

    override suspend fun getCriticalAlerts(): Response<List<AlertDto>> =
        api.getCriticalAlerts("Bearer $token")

    override suspend fun exportLogs(id: Long): Response<List<String>> =
        api.exportLogs(id, "Bearer $token")
}