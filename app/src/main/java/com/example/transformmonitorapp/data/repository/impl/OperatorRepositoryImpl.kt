package com.example.transformmonitorapp.data.repository.impl

import com.example.transformmonitorapp.data.network.api.OperatorApi
import com.example.transformmonitorapp.data.repository.interfaces.OperatorRepository
import com.example.transformmonitorapp.domain.dto.TransformerDto
import com.example.transformmonitorapp.domain.model.TransformerStatus


class OperatorRepositoryImpl (private val operatorApi: OperatorApi): OperatorRepository {
    override suspend fun getAllTransformersStatus(): List<TransformerDto> {
       return operatorApi.getAllTransformersStatus()
    }

    override suspend fun getAllTransformers(): List<TransformerDto> {
       return operatorApi.getTransformers();
    }

    override suspend fun getTransformerStatus(id: Long): TransformerStatus {
       return operatorApi.getTransformerStatus(id)
    }
}