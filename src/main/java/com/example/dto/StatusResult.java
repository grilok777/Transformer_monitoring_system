package com.example.dto;

import com.example.entity.mongo.AlertLevel;
import com.example.entity.mongo.TransformerStatus;

public record StatusResult(
        TransformerStatus status,
        boolean condition,
        String message,
        AlertLevel level
) {}