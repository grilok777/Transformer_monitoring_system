package com.example.transformmonitorapp.data.repository.interfaces

import com.example.transformmonitorapp.domain.dto.AlertDto
import com.example.transformmonitorapp.domain.dto.TransformerDto
import com.example.transformmonitorapp.domain.dto.request.TransformerRequest
import retrofit2.Response

interface AdminRepository {

    suspend fun createTransformer(req: TransformerRequest): Response<TransformerDto>

    suspend fun updateTransformer(id: Long, req: TransformerRequest): Response<TransformerDto>

    suspend fun deactivateTransformer(id: Long): Response<Void>

    suspend fun exportTransformer(id: Long): Response<TransformerDto>

    suspend fun exportTransformerRange(from: Long, to: Long): Response<List<TransformerDto>>

    suspend fun exportAllTransformers(): Response<List<TransformerDto>>

    suspend fun getAllAlerts(): Response<List<AlertDto>>

    suspend fun getCriticalAlerts(): Response<List<AlertDto>>

    suspend fun exportLogs(id: Long): Response<List<String>>
}