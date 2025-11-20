package com.example.service.impl;

import com.example.entity.mongo.Transformer;
import com.example.repository.mongo.TransformerRepository;
import com.example.service.interfaces.SimulatorService;
import com.example.service.interfaces.TransformerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimulatorServiceImpl implements SimulatorService {

    private final TransformerRepository transformerRepository;
    private final TransformerService transformerService;

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    private final double steelLoss = 2;
    private final double copperLossNominal = 10.5;

    @PostConstruct
    public void init() {
        log.info("Simulator ready. Waiting for startSimulation() calls.");
    }

    @Override
    public void startSimulation(Long transformerId) {
        Optional<Transformer> opt = transformerRepository.findById(transformerId);

        if (opt.isEmpty()) {
            log.error("Transformer {} not found. Simulation not started!", transformerId);
            return;
        }

        executor.scheduleAtFixedRate(
                () -> simulateStep(transformerId),
                5, 240, TimeUnit.SECONDS //240
        );

        log.info("Simulation started for transformer {}", transformerId);
    }

    private void simulateStep(Long transformerId) {
        try {
            Transformer t = transformerService.getById(transformerId).orElseThrow();

            double loadFactor = 0.788 + random.nextDouble() * 0.088;
            double efficiency = calculateEfficiency(t.getRatedPowerKVA(), loadFactor);
            double power = t.getRatedPowerKVA() * efficiency;
            double temperature = calculateTemperature(loadFactor);

            double num = 3.0;
            double currentAmpere = power / (Math.pow(num, 1.0 / 3.0)
                    * t.getSecondaryVoltageKV() * loadFactor);

            double voltage = (power / currentAmpere) * loadFactor;

            transformerService.updateStatus(transformerId, temperature, voltage);
            transformerService.updateData(transformerId, power, temperature, voltage);

            log.info(
                    "Load: {}% | Power: {} kW | Temp: {}°C | Volt: {} kV | Eff: {}% | Status: {}",
                    String.format("%.2f", loadFactor * 100),
                    String.format("%.2f", power),
                    String.format("%.2f", temperature),
                    String.format("%.2f", voltage),
                    String.format("%.2f", efficiency * 100),
                    t.getStatus().name()
            );

        } catch (Exception e) {
            log.error("Simulation error: {}", e.getMessage());
        }
    }

    private double calculateEfficiency(double ratedPower, double loadFactor) {
        double copperLoss = copperLossNominal * Math.pow(loadFactor, 2);
        return ratedPower * loadFactor /
                (ratedPower * loadFactor + copperLoss + steelLoss);
    }

    private double calculateTemperature(double loadFactor) {
        double ambient = 20 + random.nextDouble() * 20;
        double Rth = 7.0;
        double copperLoss = copperLossNominal * Math.pow(loadFactor, 2);
        double totalLoss = steelLoss + copperLoss;
        return ambient + totalLoss * Rth;
    }
}