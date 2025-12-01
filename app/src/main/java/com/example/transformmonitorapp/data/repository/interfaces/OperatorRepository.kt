package com.example.transformmonitorapp.data.repository.interfaces

import com.example.transformmonitorapp.domain.dto.AlertDto
import com.example.transformmonitorapp.domain.dto.TransformerDto
import com.example.transformmonitorapp.domain.model.TransformerStatus

interface OperatorRepository {

    suspend fun getAllTransformers(): List<TransformerDto>

    suspend fun getTransformerById(id: Long): TransformerDto

    suspend fun getTransformerAlerts(id: Long): List<AlertDto>

    suspend fun processTransformerError(id: Long)
}