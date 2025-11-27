package com.example.service.impl;

import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;
import com.example.dto.request.TransformerRequest;
import com.example.entity.mongo.Transformer;
import com.example.exception.TransformerNotFoundException;
import com.example.mapper.AlertMapper;
import com.example.mapper.TransformerMapper;
import com.example.entity.mongo.AlertLevel;
import com.example.service.interfaces.AdminService;

import com.example.service.interfaces.AlertService;
import com.example.service.interfaces.OperatorService;
import com.example.service.interfaces.TransformerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final TransformerService transformerService;
    private final AlertService alertService;
    private final OperatorService operatorService;


    @Override
    public void updateTransformer(Long id, TransformerRequest request) {

        Transformer existing = transformerService.getById(id)
                .orElseThrow(TransformerNotFoundException::new);

        Transformer updated = existing.toBuilder()
                .manufacturer(request.manufacturer())
                .modelType(request.modelType())
                .ratedPowerKVA(request.ratedPowerKVA())
                .primaryVoltageKV(request.primaryVoltageKV())
                .secondaryVoltageKV(request.secondaryVoltageKV())
                .frequencyHz(request.frequencyHz())
                .transformerCondition(request.transformerCondition())
                .remoteMonitoring(request.remoteMonitoring())
                .build();

        transformerService.save(updated);
    }

    @Override
    public void deactivateTransformer(Long id) {
        Transformer t = transformerService.getById(id)
                .orElseThrow(TransformerNotFoundException::new);

        Transformer updated = t.toBuilder()
                .transformerCondition(false)  // деактивація = вимкнений
                .build();

        transformerService.save(updated);
    }

    @Override
    public Optional<TransformerDto> exportTransformer(Long id) {
        return operatorService.getTransformer(id);
    }

    @Override
    public List<TransformerDto> exportTransformersRange(Long fromId, Long toId) {
        return transformerService.getAll().stream()
                .filter(t -> t.getId() >= fromId && t.getId() <= toId)
                .map(TransformerMapper::toDto)
                .toList();
    }

    @Override
    public List<TransformerDto> exportAllTransformers() {
        return operatorService.getAllTransformers().stream().toList();
    }

    @Override
    public List<AlertDto> getAllErrors() {
        return alertService.findAll().stream()
                .map(AlertMapper::toDto)
                .toList();
    }

    @Override
    public List<AlertDto> getCriticalAlerts() {
        return alertService.findAll().stream()
                .filter(a -> a.getLevel() == AlertLevel.CRITICAL)
                .map(AlertMapper::toDto)
                .toList();
    }

    @Override
    public List<String> exportTransformerLogs(Long id) {
        return transformerService.getById(id)
                .orElseThrow(TransformerNotFoundException::new)
                .getDataLogs()
                .stream()
                .map(Object::toString)
                .toList();
    }
}