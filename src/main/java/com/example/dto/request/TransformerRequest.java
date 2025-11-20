package com.example.dto.request;

public record TransformerRequest(
        String manufacturer,
        String modelType,
        Double ratedPowerKVA,
        Integer primaryVoltageKV,
        Integer secondaryVoltageKV,
        Double frequencyHz,
        Boolean transformerCondition,
        Boolean remoteMonitoring
) {}