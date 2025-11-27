package com.example.service.interfaces;

import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;
import com.example.entity.mongo.TransformerStatus;

import java.util.List;
import java.util.Optional;

public interface OperatorService {
    List<TransformerDto> getAllTransformers();

    Optional<TransformerDto> getTransformer(Long id);

    TransformerStatus getTransformerStatus(Long id);

    List<AlertDto> getTransformerAlerts(Long id);

    List<TransformerStatus> getAllTransformersStatus();

    AlertDto addErrorProcessing(Long transformerId);
}