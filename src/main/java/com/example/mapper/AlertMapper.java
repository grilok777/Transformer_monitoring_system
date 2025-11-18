package com.example.mapper;

import com.example.dto.AlertDto;
import com.example.model.Alert;

public class AlertMapper {

    public static AlertDto toDto(Alert a) {
        return new AlertDto(
                a.getId(),
                a.getTransformerId(),
                a.getMessage(),
                a.getLevel(),
                a.getTemperature(),
                a.getVoltage(),
                a.getTimestamp(),
                a.getProblemResolved()
        );
    }
}