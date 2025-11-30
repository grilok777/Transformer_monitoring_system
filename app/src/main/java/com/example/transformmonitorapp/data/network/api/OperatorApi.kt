package com.example.transformmonitorapp.data.network.api

import com.example.transformmonitorapp.domain.dto.AlertDto
import com.example.transformmonitorapp.domain.dto.TransformerDto
import com.example.transformmonitorapp.domain.dto.response.MessageResponse
import com.example.transformmonitorapp.domain.model.TransformerStatus
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface OperatorApi {


    @GET("/api/operator/transformers")
    suspend fun getAllTransformersStatus( @Header("Authorization") token: String): List<TransformerDto>


    @GET("/api/operator/transformers/{id}")
    suspend fun getTransformerStatus(
        @Path("id" ) id: Long,
         @Header("Authorization") token: String): TransformerDto

    @POST("/api/operator/transformers/{id}/process-error")
    suspend fun processrError(@Path("id") id: Long, @Header("Authorization") token: String): AlertDto
    /*public AlertDto processError(
            @PathVariable Long id//Long
    ) {
        return operatorService.addErrorProcessing(id);
    }*/

    @GET("/api/operator/transformers/{id}/alerts")
    suspend fun getAlerts(@Path("id" ) id: Long, @Header("Authorization") token: String):List<AlertDto>
}