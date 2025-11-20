package com.example.service.impl;

import com.example.dto.request.TransformerRequest;
import com.example.model.Transformer;
import com.example.model.TransformerStatus;
import com.example.repository.mongo.TransformerRepository;

import com.example.model.AlertLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TransformerService {

    @Autowired
    private TransformerRepository repository;
    private AlertService serviceA;
    private List<Map<String, Object>> dataLogs = new ArrayList<>();

    @Autowired
    public TransformerService(TransformerRepository repository, AlertService serviceA) {
        this.repository = repository;
        this.serviceA = serviceA;
    }

    public Optional<Transformer> getById(Long id) {
        return repository.findById(id);
    }

    public List<Transformer> getAll() {
        return repository.findAll();
    }

    public Transformer save(Transformer t) {
        return repository.save(t);
    }

    public TransformerStatus checkStatus(Long transformerId) {
        Transformer transformer = repository.findById(transformerId)
                .orElseThrow(() -> new RuntimeException("Transformer not found with id: " + transformerId));
        return transformer.getStatus();
    }

    public void updateData(Long id, Double power, Double temp, Double voltage) {

        Transformer t = repository.findById(id).orElseThrow();

        // 1 — перезапис полів (фактичні поточні значення)
        t.setCurrentPower(power);
        t.setCurrentTemperature(temp);
        t.setCurrentVoltage(voltage);

        // 2 — додавання в масив dataLogs
        Map<String, Object> logEntry = new HashMap<>();
        logEntry.put("timestamp", LocalDateTime.now().toString());
        logEntry.put("power", power);
        logEntry.put("temperature", temp);
        logEntry.put("voltage", voltage);
        logEntry.put("status", t.getStatus());

        t.getDataLogs().add(logEntry);

        // 3 — зберегти в MongoDB
        repository.save(t);
    }

    // --- Оновлення статусу (перероблений метод) ---
    public void updateStatus(Long transformerId, double temp, double voltage) {//Long
        Transformer transformer = repository.findById(transformerId)
                .orElseThrow(() -> new RuntimeException("Transformer not found with id: " + transformerId));

        if (temp > 110) {
            transformer.setStatus(TransformerStatus.ERROR);
            transformer.setTransformerCondition(Boolean.valueOf(false));
            serviceA.createAndSaveAlert(transformerId, "Критичне перегрівання (>110°C)", AlertLevel.ERROR, Double.valueOf(temp), Double.valueOf(voltage));
        } else if (temp > 100) {
            transformer.setStatus(TransformerStatus.CRITICAL);
            transformer.setTransformerCondition(Boolean.valueOf(false));
            serviceA.createAndSaveAlert(transformerId, "Висока температура (>100°C)", AlertLevel.CRITICAL, Double.valueOf(temp), Double.valueOf(voltage));
        } else if (transformer.getSecondaryVoltageKV() != null &&
                (voltage < transformer.getSecondaryVoltageKV() * 0.90 ||
                        voltage > transformer.getSecondaryVoltageKV() * 1.10)) {
            transformer.setStatus(TransformerStatus.ERROR);
            transformer.setTransformerCondition(Boolean.valueOf(false));
            serviceA.createAndSaveAlert(transformerId, "Відхилення напруги ±10%", AlertLevel.ERROR, Double.valueOf(temp), Double.valueOf(voltage));
        } else {
            transformer.setStatus(TransformerStatus.NORMAL);
            // Можливо, ви захочете встановити transformerCondition в true тут
            transformer.setTransformerCondition(Boolean.valueOf(true));
        }

        repository.save(transformer);

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
