package com.example.service.impl;

import com.example.model.Transformer;

import com.example.repository.mongo.AlertRepository;
import com.example.repository.mongo.TransformerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimulatorService {

    private final TransformerRepository transformerRepository;
    private final AlertRepository alertRepository;
    private final AlertService alertService;
    private final TransformerService transformerService;

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);
    private final Random random = new Random();

    private final double steelLoss = 2;
    private final double copperLossNominal = 10.5;


    @PostConstruct
    public void init() {
        log.info("Simulator ready. Waiting for startSimulation() calls.");
    }


    // ---------------- Запуск симуляції ----------------

    public void startSimulation(Long transformerId) {//Long
        Optional<Transformer> opt = transformerRepository.findById(transformerId);

        if (opt.isEmpty()) {
            log.error("Transformer {} not found. Simulation not started!", transformerId);
            return;
        }

        executor.scheduleAtFixedRate(
                () -> simulateStep(transformerId),
                5, 20, TimeUnit.SECONDS //240
        );

        log.info("Simulation started for transformer {}", transformerId);
    }


    // ---------------- Один крок симуляції ----------------

    private void simulateStep(Long transformerId) {//Long
        try {
            Transformer t = transformerService.getById(transformerId).orElseThrow();

            double loadFactor = 0.788 + random.nextDouble() * 0.088; //0.788-0.876
            double efficiency = calculateEfficiency(t.getRatedPowerKVA(), loadFactor);
            double power = t.getRatedPowerKVA() * efficiency;
            double temperature = calculateTemperature(loadFactor);

            double num = 3.0;
            double currentAmpere = power / (Math.pow(num, 1.0 / 3.0)
                    * t.getSecondaryVoltageKV() * loadFactor);

            double voltage = (power / currentAmpere) * loadFactor;
            //voltage = (power / (power / (Math.pow(num, 1.0 / 3.0) * t.getSecondaryVoltageKV() * loadFactor))) * loadFactor;
            // оновити статус (через сервіс)
            transformerService.updateStatus(transformerId, temperature, voltage);
            //Transformer
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


    // ---------------- Розрахунки ----------------

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