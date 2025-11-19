package com.example.mapper;

import com.example.dto.AlertDto;
import com.example.entity.mongo.Alert;

public class AlertMapper {

    public static AlertDto toDto(Alert alert) {
        if (alert == null) throw new NullPointerException();

        return new AlertDto(
                alert.getId(),
                alert.getTransformerId(),
                alert.getMessage(),
                alert.getLevel(),
                alert.getTemperature(),
                alert.getVoltage(),
                alert.getTimestamp(),
                alert.getProblemResolved() != null && alert.getProblemResolved() == 1
        );
    }

    public static Alert toEntity(AlertDto dto) {
        if (dto == null) throw new NullPointerException();

        return Alert.builder()
                .id(dto.id())
                .transformerId(dto.transformerId())
                .message(dto.message())
                .level(dto.level())
                .temperature(dto.temperature())
                .voltage(dto.voltage())
                .timestamp(dto.timestamp())
                .problemResolved(dto.problemResolved() != null && dto.problemResolved() ? 1 : 0)
                .build();
    }
}