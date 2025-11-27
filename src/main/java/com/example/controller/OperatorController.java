package com.example.controller;

import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;
import com.example.entity.mongo.TransformerStatus;
import com.example.service.interfaces.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operator")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMIN', 'DATA_ANALYST')")
@RequiredArgsConstructor
public class OperatorController {

    private final OperatorService operatorService;

    @GetMapping("/transformers")
    public ResponseEntity<List<TransformerDto>> getAllTransformers() {
        return ResponseEntity.ok(operatorService.getAllTransformers());
    }

    @GetMapping("/transformers-status")
    public ResponseEntity<List<TransformerStatus>> getAllTransformersStatus() {
        return ResponseEntity.ok(operatorService.getAllTransformersStatus());
    }

    @GetMapping("/transformers/{id}")
    public ResponseEntity<TransformerStatus> getTransformer(@PathVariable Long id) {
        return ResponseEntity.ok(operatorService.getTransformerStatus(id));
    }

    @GetMapping("/transformers/{id}/alerts")
    public ResponseEntity<List<AlertDto>> getAlerts(@PathVariable Long id) {
        return ResponseEntity.ok(operatorService.getTransformerAlerts(id));
    }

    @PostMapping("/transformers/{id}/process-error")
    public ResponseEntity<AlertDto> processError(@PathVariable Long id) {
        return ResponseEntity.ok(operatorService.addErrorProcessing(id));
    }
}