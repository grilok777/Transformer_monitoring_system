package com.example.service.impl;

import com.example.dto.request.TransformerRequest;
import com.example.entity.mongo.AlertLevel;
import com.example.entity.mongo.Transformer;
import com.example.entity.mongo.TransformerStatus;
import com.example.exception.TransformerNotFoundException;
import com.example.repository.mongo.TransformerRepository;
import com.example.service.interfaces.AlertService;
import com.example.service.interfaces.TransformerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransformerServiceImpl implements TransformerService {

    private final TransformerRepository repository;
    private final AlertService alertService;

    private static final double TEMP_CRITICAL = 100.0;
    private static final double TEMP_ERROR = 110.0;
    private static final double VOLTAGE_DEVIATION = 0.10; // ±10%

    @Override
    public Optional<Transformer> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Transformer> getAll() {
        return repository.findAll();
    }

    @Override
    public Transformer save(Transformer transformer) {
        log.info("Saving transformer: {}", transformer.getId());
        return repository.save(transformer);
    }

    @Override
    public TransformerStatus checkStatus(Long transformerId) {
        Transformer transformer = repository.findById(transformerId)
                .orElseThrow(TransformerNotFoundException::new);
        return transformer.getStatus();
    }

    @Override
    public void updateData(Long id, Double power, Double temp, Double voltage) {
        Transformer transformer = repository.findById(id)
                .orElseThrow(TransformerNotFoundException::new);

        transformer.setCurrentPower(power);
        transformer.setCurrentTemperature(temp);
        transformer.setCurrentVoltage(voltage);

        Map<String, Object> logEntry = new HashMap<>();
        logEntry.put("timestamp", LocalDateTime.now());
        logEntry.put("power", power);
        logEntry.put("temperature", temp);
        logEntry.put("voltage", voltage);
        logEntry.put("status", transformer.getStatus());

        transformer.getDataLogs().add(logEntry);

        updateStatusInternal(transformer, temp, voltage);

        repository.save(transformer);
        log.debug("Updated transformer data: {}", transformer.getId());
    }

    @Override
    public void updateStatus(Long transformerId, Double temp, Double voltage) {
        Transformer transformer = repository.findById(transformerId)
                .orElseThrow(TransformerNotFoundException::new);

        updateStatusInternal(transformer, temp, voltage);
        repository.save(transformer);
        log.debug("Updated transformer status: {}", transformer.getId());
    }

    private void updateStatusInternal(Transformer transformer, Double temp, Double voltage) {

        // Список правил для перевірки температури
        List<StatusRule> tempRules = List.of(
                new StatusRule(t -> t > TEMP_ERROR, TransformerStatus.ERROR, false,
                        "Критичне перегрівання (>110°C)", AlertLevel.ERROR),
                new StatusRule(t -> t > TEMP_CRITICAL, TransformerStatus.CRITICAL, false,
                        "Висока температура (>100°C)", AlertLevel.CRITICAL)
        );

        tempRules.stream()
                .filter(rule -> rule.matches(temp))
                .findFirst()
                .ifPresent(rule -> applyStatusAndAlert(transformer, rule, temp, voltage));


        Double secondaryKV = Double.valueOf(transformer.getSecondaryVoltageKV());

        if (voltage < secondaryKV * (1 - VOLTAGE_DEVIATION) || voltage > secondaryKV * (1 + VOLTAGE_DEVIATION)) {
            applyStatusAndAlert(transformer,
                    new StatusRule(t -> true, TransformerStatus.ERROR, false,
                            "Відхилення напруги ±10%", AlertLevel.ERROR),
                    temp, voltage);
            return;
        }
/*
        // Якщо нічого не спрацювало — нормальний статус
        if (!TransformerStatus.NORMAL.equals(transformer.getStatus())) {
            transformer.setStatus(TransformerStatus.NORMAL);
            transformer.setTransformerCondition(true);
            log.info("Transformer {} status updated to NORMAL", transformer.getId());
        }*/
    }

    private void applyStatusAndAlert(Transformer transformer, StatusRule rule, Double temp, Double voltage) {
        transformer.setStatus(rule.status());
        transformer.setTransformerCondition(rule.conditionFlag());
        alertService.createAndSaveAlert(transformer.getId(), rule.message(), rule.level(), temp, voltage);
        log.info("Transformer {} status updated to {}", transformer.getId(), rule.status());
    }

    private record StatusRule(
            Predicate<Double> condition,
            TransformerStatus status,
            Boolean conditionFlag,
            String message,
            AlertLevel level
    ) {
        boolean matches(Double temp) {
            return condition.test(temp);
        }
    }

    public Transformer create(TransformerRequest request) {
        Transformer t = new Transformer();
        Long newId = getNextId();

        t.setId(newId);
        t.setManufacturer(request.manufacturer());
        t.setModelType(request.modelType());
        t.setRatedPowerKVA(request.ratedPowerKVA());
        t.setPrimaryVoltageKV(request.primaryVoltageKV());
        t.setSecondaryVoltageKV(request.secondaryVoltageKV());
        t.setFrequencyHz(request.frequencyHz());
        t.setTransformerCondition(request.transformerCondition());
        t.setRemoteMonitoring(request.remoteMonitoring());

        return repository.save(t);
    }

    public Transformer update(Long id, TransformerRequest request) {
        Transformer t = repository.findById(id).orElseThrow();

        if (request.manufacturer() != null) t.setManufacturer(request.manufacturer());
        if (request.modelType() != null) t.setModelType(request.modelType());
        if (request.ratedPowerKVA() != null) t.setRatedPowerKVA(request.ratedPowerKVA());
        if (request.primaryVoltageKV() != null) t.setPrimaryVoltageKV(request.primaryVoltageKV());
        if (request.secondaryVoltageKV() != null) t.setSecondaryVoltageKV(request.secondaryVoltageKV());
        if (request.frequencyHz() != null) t.setFrequencyHz(request.frequencyHz());
        if (request.transformerCondition() != null) t.setTransformerCondition(request.transformerCondition());

        return repository.save(t);
    }

    public void deactivate(Long id) {
        Transformer t = repository.findById(id).orElseThrow();
        t.setTransformerCondition(false);
        repository.save(t);
    }

    private Long getNextId() {
        return repository.findTopByOrderByIdDesc()
                .map(t -> t.getId())
                .map(id -> id + 1)                            // +1 коректно
                .orElse(1L);
    }
}
