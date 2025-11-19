package com.example.dto;

import com.example.entity.mongo.TransformerStatus;

public record TransformerDto(
        Long id,
        String manufacturer,
        String modelType,
        Double ratedPowerKVA,
        Integer primaryVoltageKV,
        Integer secondaryVoltageKV,
        Double frequencyHz,
        Boolean transformerCondition,
        Boolean remoteMonitoring,
        Double currentPower,
        Double currentTemperature,
        Double currentVoltage,
        TransformerStatus status
) {}