package com.example.transformmonitorapp.domain.dto

import com.example.transformmonitorapp.domain.model.TransformerStatus

data class TransformerDto(
    val id: Long?,
    val manufacturer: String?,
    val modelType: String?,
    val ratedPowerKVA: Double?,
    val primaryVoltageKV: Int?,
    val secondaryVoltageKV: Int?,
    val frequencyHz: Double?,
    val transformerCondition: Boolean?,
    val remoteMonitoring: Boolean?,
    val currentPower: Double?,
    val currentTemperature: Double?,
    val currentVoltage: Double?,
    val status: TransformerStatus?
)