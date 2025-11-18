package com.example.service.interfaces;

import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;
import com.example.model.Transformer;
import com.example.model.Alert;
import java.util.List;

public interface OperatorService {

    List<TransformerDto> getAllTransformersStatus();

    TransformerDto getTransformerStatus(Long id);

    Alert addErrorProcessing(Long Id);

    List<AlertDto> getTransformerAlerts(Long transformerId);
}