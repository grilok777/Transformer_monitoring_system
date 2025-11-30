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


    public void createTransformer(TransformerRequest request) {
        TransformerMapper.toDto(transformerService.create(request));
    }

    @Override
    public void updateTransformer(Long id, TransformerRequest request) {
        TransformerMapper.toDto(transformerService.update(id, request));
    }

    @Override
    public void deactivateTransformer(Long id) {
        transformerService.deactivate(id);
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
        return operatorService.getAllTransformers().stream().toList();////;
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