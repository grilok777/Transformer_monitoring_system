package com.example.dto.request;

import com.example.entity.mongo.AlertLevel;

public record AlertRequest(
        Long transformerId,
        String message,
        AlertLevel level,
        Double temperature,
        Double voltage
) {}