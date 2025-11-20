package com.example.dto;

import com.example.entity.mongo.AlertLevel;

public record AlertDto(
        Long id,
        Long transformerId,
        String message,
        AlertLevel level,
        Double temperature,
        Double voltage,
        String timestamp,
        Boolean problemResolved
) {}