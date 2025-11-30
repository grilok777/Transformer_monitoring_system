package com.example.controller;

import com.example.dto.response.MessageResponse;
import com.example.service.interfaces.DataAnalystService;
import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analyst")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

@PreAuthorize("hasAuthority('DATA_ANALYST')")
public class DataAnalystController {
    private final DataAnalystService analystService;

    @GetMapping("/send")
    public MessageResponse sendReport(){
        return new MessageResponse("");
    }

    @GetMapping("/transformer/export/{id}")
    public ResponseEntity<TransformerDto> getTransformer(@PathVariable("id") Long id) {

        return ResponseEntity.ok(analystService.exportTransformer(id));
    }

    @GetMapping("/transformers/export/{from}/{to}")
    public ResponseEntity<List<TransformerDto>> getTransformersRange(@PathVariable("from") Long from,
                                                     @PathVariable("to") Long to) {
        return ResponseEntity.ok(analystService.exportTransformersRange(from, to));
    }

    @GetMapping("/transformers/export/all")
    public ResponseEntity<List<TransformerDto>> getAll() {

        return ResponseEntity.ok(analystService.exportAllTransformers());
    }

    @GetMapping("/alerts")
    public ResponseEntity<List<AlertDto>> getAllAlerts() {

        return ResponseEntity.ok(analystService.getAllErrors());
    }

    @GetMapping("/alerts/critical")
    public ResponseEntity<List<AlertDto>> getCriticalAlerts() {

        return ResponseEntity.ok(analystService.getCriticalAlerts());
    }

    @GetMapping("/logs/export/{id}")
    public ResponseEntity<List<String>> getLogs(@PathVariable("id") Long id) {
        return ResponseEntity.ok(analystService.exportTransformerLogs(id));
    }
}