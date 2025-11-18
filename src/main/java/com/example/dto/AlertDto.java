package com.example.dto;

import com.example.model.AlertLevel;

public record AlertDto(
        Long id,
        Long transformerId,
        String message,
        AlertLevel level,
        Double temperature,
        Double voltage,
        String timestamp,
        int problemResolved
) {
    @Override
    public String toString() {
        return "{id}=" + id +
                " {transformerId}=" + transformerId +
                " {level}=" + level +
                " {description}=" + message +
                " {temperature}=" + temperature +
                " {voltage}=" + voltage +
                " {timestamp}=" + timestamp +
                " {resolved}=" + problemResolved;
    }
}
