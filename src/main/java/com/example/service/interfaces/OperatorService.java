package com.example.service.interfaces;

import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;
import java.util.List;

public interface OperatorService {
    List<TransformerDto> getAllTransformersStatus();

    TransformerDto getTransformerStatus(Long id);

    List<AlertDto> getTransformerAlerts(Long id);

    AlertDto addErrorProcessing(Long transformerId);
}