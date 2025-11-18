package com.example;

import com.example.model.Transformer;
import com.example.model.TransformerStatus;
import com.example.repository.TransformerRepository;
import com.example.service.impl.SimulatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StarterSimulator {

    private final TransformerRepository transformerRepository;
    private final SimulatorService simulatorService;

    @PostConstruct
    public void startActiveTransformers() {
        log.info("Searching for active transformers...");

        List<Transformer> transformers = transformerRepository.findAll();

        if (transformers.isEmpty()) {
            log.warn("No transformers found in DB. Nothing to simulate.");
            return;
        }

        transformers.stream()
                .filter(t -> Boolean.TRUE.equals(t.getTransformerCondition())) // тільки робочі
                .forEach(t -> {
                    log.info("Starting simulation for transformer ID {}", t.getId());
                    simulatorService.startSimulation(t.getId());
                });

        log.info("All active transformers started.");
    }
}