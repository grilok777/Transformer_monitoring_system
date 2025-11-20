package com.example.service.interfaces;

import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;
import com.example.dto.request.TransformerRequest;

import java.util.List;

public interface AdminService  {

    TransformerDto exportTransformer(Long id);

    List<TransformerDto> exportTransformersRange(Long fromId, Long toId);

    List<TransformerDto> exportAllTransformers();

    List<AlertDto> getAllErrors();

    List<AlertDto> getCriticalAlerts();

    List<String> exportTransformerLogs(Long id);

    TransformerDto createTransformer(TransformerRequest request);

    TransformerDto updateTransformer(Long id, TransformerRequest request);

    void deactivateTransformer(Long id);
}