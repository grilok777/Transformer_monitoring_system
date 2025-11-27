package com.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "simulator")
public class SimulationProperties {

    private double steelLoss;
    private double copperLossNominal;

    private Schedule schedule = new Schedule();
    private LoadFactor loadFactor = new LoadFactor();

    @Data
    public static class Schedule {
        private int delaySeconds;
        private int intervalSeconds;
    }

    @Data
    public static class LoadFactor {
        private double min;
        private double max;
    }
}