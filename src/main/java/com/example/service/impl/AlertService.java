/*package com.example.service.impl;

import com.example.entity.mongo.Alert;
import com.example.entity.mongo.AlertLevel;
import com.example.repository.mongo.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlertService {

    private final AlertRepository repository;

    @Autowired
    public AlertService(AlertRepository repository) {
        this.repository = repository;
    }


    private Long getNextId() {
        Optional<Alert> lastAlert = repository.findTopByOrderByIdDesc();
        // Якщо останній алерт існує, беремо його ID і додаємо 1, інакше починаємо з 1.
        long nextId = lastAlert
                .map(alert -> alert.getId())// .map(alert -> Long.parseLong(alert.getId()))   // перетворюємо у long
                .map(id -> id + 1)                            // +1 коректно
                .orElse(1L);                                  // якщо немає записів → ID = 1

        return nextId;//String.valueOf(nextId)
    }


    public Alert createAndSaveAlert(Long transformerId, String message, AlertLevel level, Double temp, Double volt) {
        Long newId = getNextId();


        Alert alert = new Alert(
                newId,
                transformerId,
                message,
                level,
                temp,
                volt,
                LocalDateTime.now().toString(),
                (level == AlertLevel.ERROR || level == AlertLevel.CRITICAL ? 0 : 1)
        );

        return repository.save(alert);
    }


    public Alert create(Alert alert) {
        // Цей метод може бути корисним, якщо ви хочете зберегти Alert,
        // ID якого вже встановлено десь в іншому місці.
        return repository.save(alert);
    }

    public List<Alert> getActiveAlerts() {
        return repository.findAll()
                .stream()
                .filter(a -> a.getProblemResolved() == 0)
                .collect(Collectors.toList());
    }

    public List<Alert> getAlertsByTransformerId(Long transformerId) {//Long
        return repository.findByTransformerId(transformerId);
    }

    public Alert createOperatorNote(Long id) {
        // Знаходимо існуючий alert
        Alert alert = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found: " + id));

        // Оновлюємо тільки потрібне поле
        alert.setProblemResolved(1);

        // Зберігаємо назад
        return repository.save(alert);
    }


    public List<Alert> findAll() {
        return repository.findAll(); // ← Реалізація
    }
}*/