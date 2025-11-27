package com.example.service.impl;

import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;
import com.example.exception.TransformerNotFoundException;
import com.example.mapper.AlertMapper;
import com.example.mapper.TransformerMapper;
import com.example.entity.mongo.AlertLevel;
import com.example.service.interfaces.AlertService;
import com.example.service.interfaces.DataAnalystService;
import com.example.service.interfaces.OperatorService;
import com.example.service.interfaces.TransformerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DataAnalystServiceImpl implements DataAnalystService {

    private final OperatorService operatorService;
    private final TransformerService transformerService;
    private final AlertService alertService;

    @Override
    public TransformerDto exportTransformer(Long id) {
        return operatorService.getTransformer(id)
                .orElseThrow(TransformerNotFoundException::new);
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
        return transformerService.getAll()
                .stream()
                .map(TransformerMapper::toDto)
                .toList();
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