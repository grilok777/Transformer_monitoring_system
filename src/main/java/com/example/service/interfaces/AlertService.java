package com.example.service.interfaces;

import com.example.dto.AlertDto;
import com.example.dto.request.AlertRequest;
import com.example.entity.mongo.Alert;
import com.example.entity.mongo.AlertLevel;

import java.util.List;

public interface AlertService {

    void createAndSaveAlert(Long transformerId, String message, AlertLevel level, Double temp, Double volt);

    Alert create(AlertRequest request);

    List<AlertDto> getActiveAlerts();

    void createOperatorNote(Long id);

    List<Alert> findAll();

    List<Alert> getAlertsByTransformerId(Long transformerId);
}