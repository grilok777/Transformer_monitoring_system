package com.example.service.impl;

import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;
import com.example.mapper.AlertMapper;
import com.example.mapper.TransformerMapper;
import com.example.service.interfaces.AlertService;
import com.example.service.interfaces.OperatorService;
import com.example.service.interfaces.TransformerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OperatorServiceImpl implements OperatorService {

    private final TransformerService transformerService;
    private final AlertService alertService;


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

    //@Override
    //public List<AlertDto> getTransformerAlerts(Long id) {
    //    return List.of();
    //}
//
    //@Override
    //public AlertDto addErrorProcessing(Long transformerId) {
    //    return null;
    //}

    @Override
    public List<AlertDto> getTransformerAlerts(Long id) {
        return alertService.getAlertsByTransformerId(id)
                .stream()
                .map(AlertMapper::toDto)
                .toList();
    }
    @Override
    public AlertDto addErrorProcessing(Long transformerId) {//String
        // оператор залишає коментар до існуючої помилки
        alertService.createOperatorNote(transformerId);
        return null;
    }
}