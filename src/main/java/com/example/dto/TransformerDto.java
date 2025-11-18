package com.example.dto;

public record TransformerDto(
        Long id,
        String manufacturer,
        String modelType,
        double ratedPowerKVA,
        int primaryVoltageKV,
        int secondaryVoltageKV,
        double frequencyHz,
        boolean transformerCondition,
        boolean remoteMonitoring,
        String status
) {
    @Override
    public String toString() {
        return "{id}=" + id +
                " {manufacturer}=" + manufacturer +
                " {modelType}=" + modelType +
                " {ratedPowerKVA}=" + ratedPowerKVA +
                " {primaryVoltageKV}=" + primaryVoltageKV +
                " {secondaryVoltageKV}=" + secondaryVoltageKV +
                " {frequencyHz}=" + frequencyHz +
                " {condition}=" + transformerCondition +
                " {remoteMonitoring}=" + remoteMonitoring +
                " {status}=" + status;
    }
}
