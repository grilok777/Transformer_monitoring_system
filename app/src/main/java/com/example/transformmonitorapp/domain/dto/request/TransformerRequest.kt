package com.example.transformmonitorapp.domain.dto.request

data class TransformerRequest(
    val manufacturer: String,
    val modelType: String,
    val ratedPowerKVA: Double,
    val primaryVoltageKV: Int,
    val secondaryVoltageKV: Int,
    val frequencyHz: Double,
    // ці два поля потрібні для оновлення, при створенні можна їх не передавати (null)
    val transformerCondition: Boolean? = null,
    val remoteMonitoring: Boolean? = null
)


