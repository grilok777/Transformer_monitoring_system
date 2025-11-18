package com.example.service.impl;

import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;
import com.example.mapper.AlertMapper;
import com.example.mapper.TransformerMapper;
import com.example.model.Alert;
import com.example.model.Transformer;
import com.example.service.interfaces.OperatorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatorServiceImpl implements OperatorService {

    private final TransformerService transformerService;
    private final AlertService alertService;

    public OperatorServiceImpl(
            TransformerService transformerService,
            AlertService alertService
    ) {
        this.transformerService = transformerService;
        this.alertService = alertService;
    }
    @Override
    public List<TransformerDto> getAllTransformersStatus() {
        return transformerService.getAll()
                .stream()
                .map(TransformerMapper::toDto)
                .toList();
    }

    @Override
    public TransformerDto getTransformerStatus(Long id) {
        return transformerService
                .getById(id)
                .map(TransformerMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Трансформатор не знайдено"));
    }

    @Override
    public List<AlertDto> getTransformerAlerts(Long id) {
        return alertService.getAlertsByTransformerId(id)
                .stream()
                .map(AlertMapper::toDto)
                .toList();
    }
    @Override
    public Alert addErrorProcessing(Long transformerId) {//String
        // оператор залишає коментар до існуючої помилки
        alertService.createOperatorNote(transformerId);
        return null;
    }
/*
    @Override
    public List<Transformer> getAllTransformersStatus() {
        return transformerService.getAll();
    }

    @Override
    public Transformer getTransformerStatus(Long id) {//String
        return transformerService
                .getById(id)
                .orElseThrow(() -> new RuntimeException("Трансформатор не знайдено"));
    }
*/

/*
    @Override
    public List<Alert> getTransformerAlerts(Long Id) {//String
        return alertService.getAlertsByTransformerId(Id);
    }*/
}