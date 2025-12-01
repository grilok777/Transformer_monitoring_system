package com.example.transformmonitorapp.domain.dto.request

data class TransformerRequest(
    val manufacturer: String,
    val modelType: String,
    val ratedPowerKVA: Double,
    val primaryVoltageKV: Int,
    val secondaryVoltageKV: Int,
    val frequencyHz: Double,
    val transformerCondition: Boolean? = null,
    val remoteMonitoring: Boolean? = null
)