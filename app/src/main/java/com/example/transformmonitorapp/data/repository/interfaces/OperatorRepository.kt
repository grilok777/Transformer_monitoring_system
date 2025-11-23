package com.example.transformmonitorapp.data.repository.interfaces

import com.example.transformmonitorapp.domain.dto.TransformerDto
import com.example.transformmonitorapp.domain.model.TransformerStatus

interface OperatorRepository {
    suspend fun getAllTransformersStatus(): List<TransformerDto>

    suspend fun getAllTransformers(): List<TransformerDto>

    suspend fun getTransformerStatus(id : Long): TransformerStatus
}