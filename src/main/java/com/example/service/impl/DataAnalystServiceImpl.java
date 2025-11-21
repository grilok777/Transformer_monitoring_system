package com.example.service.impl;

import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;
import com.example.mapper.AlertMapper;
import com.example.mapper.TransformerMapper;
import com.example.entity.mongo.AlertLevel;
import com.example.service.interfaces.DataAnalystService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DataAnalystServiceImpl implements DataAnalystService {

    private final OperatorServiceImpl operatorService;
    private final TransformerServiceImpl transformerService;
    private final AlertServiceImpl alertService;



    @Override
    public TransformerDto exportTransformer(Long id) {
        return operatorService.getTransformerStatus(id);
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
        return operatorService.getAllTransformersStatus().stream()
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
                .orElseThrow(() -> new RuntimeException("Трансформатор не знайдено"))
                .getDataLogs()
                .stream()
                .map(Object::toString)
                .toList();
    }
}

