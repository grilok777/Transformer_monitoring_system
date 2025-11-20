package com.example.dto;

public record SimulationResultDto(
        Double power,
        Double temperature,
        Double voltage,
        Double efficiency,
        Double loadFactor
) {
}