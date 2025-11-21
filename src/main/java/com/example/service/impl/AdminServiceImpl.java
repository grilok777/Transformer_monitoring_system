package com.example.service.impl;

import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;
import com.example.dto.request.TransformerRequest;
import com.example.mapper.AlertMapper;
import com.example.mapper.TransformerMapper;
import com.example.entity.mongo.AlertLevel;
import com.example.service.interfaces.AdminService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.entity.mongo.Transformer;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final TransformerServiceImpl transformerService;
    private final AlertServiceImpl alertService;
    private final OperatorServiceImpl operatorService;


    @Override
    public TransformerDto createTransformer(TransformerRequest request) {
        return TransformerMapper.toDto(transformerService.create(request));
    }

    @Override
    public TransformerDto updateTransformer(Long id, TransformerRequest request) {
        return TransformerMapper.toDto(transformerService.update(id, request));
    }

    @Override
    public void deactivateTransformer(Long id) {
        transformerService.deactivate(id);
    }

    // --- Methods inherited from DataAnalystService ----

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

