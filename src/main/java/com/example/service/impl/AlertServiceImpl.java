package com.example.service.impl;

import com.example.dto.AlertDto;
import com.example.dto.request.AlertRequest;
import com.example.entity.mongo.Alert;
import com.example.entity.mongo.AlertLevel;
import com.example.mapper.AlertMapper;
import com.example.repository.mongo.AlertRepository;
import com.example.service.interfaces.AlertService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AlertServiceImpl implements AlertService {
    private final AlertRepository repository;


    private Long getNextId() {
        return repository.findTopByOrderByIdDesc()
                .map(Alert::getId)
                .map(id -> id + 1)
                .orElse(1L);
    }
/*
    @Override
    public void createAndSaveAlert(Long transformerId, String message, AlertLevel level, Double temp, Double volt) {
        repository.save(
                new Alert(
                getNextId(),
                transformerId,
                message,
                level,
                temp,
                volt,
                LocalDateTime.now().toString(),
                (level == AlertLevel.ERROR || level == AlertLevel.CRITICAL ? 0 : 1)
        ));
    }
**/
    public void createAndSaveAlert(Long transformerId, String message, AlertLevel level, Double temp, Double volt) {
        create(new AlertRequest(transformerId, message, level, temp, volt));
    }

    @Override
    public Alert create(AlertRequest request) {
        Alert alert = new Alert(
                getNextId(),
                request.transformerId(),
                request.message(),
                request.level(),
                request.temperature(),
                request.voltage(),
                LocalDateTime.now().toString(),
                (request.level() == AlertLevel.ERROR || request.level() == AlertLevel.CRITICAL ? 0 : 1)
        );

        return repository.save(alert);
    }

    @Override
    public List<AlertDto> getActiveAlerts() {
        return repository.findAll()
                .stream()
                .filter(a -> a.getProblemResolved() == 0)
                .map(AlertMapper::toDto)
                .toList();
    }


    @Override
    public void createOperatorNote(Long id) {
        Alert alert = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found: " + id));

        alert.setProblemResolved(1);
        repository.save(alert);
    }
    @Override
    public List<Alert> findAll() {
        return repository.findAll(); // ← Реалізація
    }

    @Override
    public List<Alert> getAlertsByTransformerId(Long transformerId) {//Long
        return repository.findByTransformerId(transformerId);
    }

}