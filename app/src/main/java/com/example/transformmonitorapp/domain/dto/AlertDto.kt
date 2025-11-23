package com.example.transformmonitorapp.domain.dto

import com.example.transformmonitorapp.domain.model.AlertLevel

data class AlertDto(
    val id: Long,
    val transformerId: Long,
    val message: String,
    val level: AlertLevel,
    val temperature: Double,
    val voltage: Double,
    val timestamp: String,
    val problemResolved: Boolean
)
