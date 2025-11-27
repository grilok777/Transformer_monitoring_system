package com.example.service.impl;

import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;
import com.example.entity.mongo.Transformer;
import com.example.entity.mongo.TransformerStatus;
import com.example.exception.TransformerNotFoundException;
import com.example.mapper.AlertMapper;
import com.example.mapper.TransformerMapper;
import com.example.service.interfaces.AlertService;
import com.example.service.interfaces.OperatorService;
import com.example.service.interfaces.TransformerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OperatorServiceImpl implements OperatorService {

    private final TransformerService transformerService;
    private final AlertService alertService;


    @Override
    public List<TransformerDto> getAllTransformers() {
        return transformerService.getAll()
                .stream()
                .map(TransformerMapper::toDto)
                .toList();
    }

    @Override
    public Optional<TransformerDto> getTransformer(Long id) {
        return Optional.ofNullable(transformerService.getById(id)
                        .map(TransformerMapper::toDto)
                        .orElseThrow(TransformerNotFoundException::new));
    }

    @Override
    public TransformerStatus getTransformerStatus(Long id) {
        return transformerService
                .getById(id)
                .map(Transformer::getStatus)
                .orElseThrow(TransformerNotFoundException::new);
    }

    @Override
    public List<AlertDto> getTransformerAlerts(Long id) {
        return alertService.getAlertsByTransformerId(id)
                .stream()
                .map(AlertMapper::toDto)
                .toList();
    }

    @Override
    public List<TransformerStatus> getAllTransformersStatus() {
        return transformerService.getAll()
                .stream()
                .map(Transformer::getStatus)
                .toList();
    }

    @Override
    public AlertDto addErrorProcessing(Long transformerId) {
        alertService.createOperatorNote(transformerId);
        return null;
    }
}