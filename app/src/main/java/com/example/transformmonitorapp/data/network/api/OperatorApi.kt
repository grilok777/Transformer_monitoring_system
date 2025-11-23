package com.example.transformmonitorapp.data.network.api

import com.example.transformmonitorapp.domain.dto.AlertDto
import com.example.transformmonitorapp.domain.dto.TransformerDto
import com.example.transformmonitorapp.domain.dto.response.MessageResponse
import com.example.transformmonitorapp.domain.model.TransformerStatus
import retrofit2.http.GET
import retrofit2.http.Path

interface OperatorApi {
    @GET("/api/transformers")
    suspend fun getTransformers(): List<TransformerDto>

    @GET("/transformers-status")
    suspend fun getAllTransformersStatus(): List<TransformerDto>

    @GET("/transformers/{id}")
    suspend fun getTransformerStatus(@Path("id") id: Long): TransformerStatus

//    @PostMapping("/transformers/{id}/process-error")
//    public AlertDto processError(
//            @PathVariable Long id//Long
//    ) {
//        return operatorService.addErrorProcessing(id);
//    }

    @GET("/transformers/{id}/alerts")
    suspend fun getAlerts(@Path("id") id: Long):List<AlertDto>
}