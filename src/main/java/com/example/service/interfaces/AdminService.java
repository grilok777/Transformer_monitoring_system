package com.example.service.interfaces;

import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;
import com.example.dto.request.TransformerRequest;

import java.util.List;
import java.util.Optional;

public interface AdminService  {

    Optional<TransformerDto> exportTransformer(Long id);

    List<TransformerDto> exportTransformersRange(Long fromId, Long toId);

    List<TransformerDto> exportAllTransformers();

    List<AlertDto> getAllErrors();

    List<AlertDto> getCriticalAlerts();

    List<String> exportTransformerLogs(Long id);

    void createTransformer(TransformerRequest request);

    void updateTransformer(Long id, TransformerRequest request);

    void deactivateTransformer(Long id);
}