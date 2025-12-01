package com.example.transformmonitorapp.data.repository.impl

import android.content.Context
import com.example.transformmonitorapp.data.network.ApiServiceProvider
import com.example.transformmonitorapp.data.repository.interfaces.AdminRepository
import com.example.transformmonitorapp.domain.dto.AlertDto
import com.example.transformmonitorapp.domain.dto.TransformerDto
import com.example.transformmonitorapp.domain.dto.request.TransformerRequest
import retrofit2.Response

class AdminRepositoryImpl(context: Context, private val token: String) : AdminRepository{
    private val api = ApiServiceProvider.adminApi(context)
    override suspend fun createTransformer(req: TransformerRequest): Response<TransformerDto> =
        api.createTransformer(req, "Bearer $token")

    override suspend fun updateTransformer(id: Long, req: TransformerRequest): Response<TransformerDto> =
        api.updateTransformer(id, req,"Bearer $token")

    override suspend fun deactivateTransformer(id: Long): Response<Void> =
        api.deactivateTransformer(id,"Bearer $token")

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