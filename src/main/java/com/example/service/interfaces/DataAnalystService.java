package com.example.service.interfaces;

import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;

import java.util.List;

public interface DataAnalystService {

    TransformerDto exportTransformer(Long id);

    List<TransformerDto> exportTransformersRange(Long fromId, Long toId);

    List<TransformerDto> exportAllTransformers();

    List<AlertDto> getAllErrors();

    List<AlertDto> getCriticalAlerts();

    List<String> exportTransformerLogs(Long id);
}