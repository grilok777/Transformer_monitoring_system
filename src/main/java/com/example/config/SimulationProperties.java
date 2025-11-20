package com.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "simulation")
@Data
public class SimulationProperties {

    private double steelLoss;
    private double copperLossNominal;

    private double loadMin;
    private double loadMax;

    private double ambientMin;
    private double ambientMax;

    private double thermalResistance;

    private int initialDelaySec;
    private int periodSec;
}