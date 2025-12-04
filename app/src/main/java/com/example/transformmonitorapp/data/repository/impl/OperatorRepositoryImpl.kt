package com.example.transformmonitorapp.data.repository.impl

import android.content.Context
import com.example.transformmonitorapp.data.network.ApiServiceProvider
import com.example.transformmonitorapp.data.network.api.OperatorApi
import com.example.transformmonitorapp.data.repository.interfaces.CreatorRepository
import com.example.transformmonitorapp.data.repository.interfaces.OperatorRepository
import com.example.transformmonitorapp.domain.dto.AlertDto
import com.example.transformmonitorapp.domain.dto.TransformerDto


class OperatorRepositoryImpl (
    context: Context,
    private val token: String
) : OperatorRepository {
    private val operatorApi = ApiServiceProvider.operatorApi(context)
    override suspend fun getAllTransformers(): List<TransformerDto> {
        return operatorApi.getAllTransformersStatus("Bearer $token")
    }

    override suspend fun getTransformerById(id: Long): TransformerDto {
        return operatorApi.getTransformerStatus(id, "Bearer $token")
    }

    override suspend fun getTransformerAlerts(id: Long): List<AlertDto> {
        return operatorApi.getAlerts(id, "Bearer $token")
    }

    override suspend fun processTransformerError(id: Long) {
        operatorApi.processError(id, "Bearer $token")
    }
}