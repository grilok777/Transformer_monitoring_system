package com.example.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;
import com.example.dto.request.TransformerRequest;
import com.example.service.interfaces.AdminService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('Admin')")
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;

    // --- CREATE TRANSFORMER ---
    @PostMapping("/transformer")
    public TransformerDto create(@RequestBody TransformerRequest request) {
        return adminService.createTransformer(request);
    }

    // --- UPDATE TRANSFORMER ---
    @PutMapping("/transformer/{id}")
    public TransformerDto update(@PathVariable Long id,
                                 @RequestBody TransformerRequest request) {
        return adminService.updateTransformer(id, request);
    }

    // --- DEACTIVATE TRANSFORMER ---
    @DeleteMapping("/transformer/{id}")
    public void deactivate(@PathVariable Long id) {
        adminService.deactivateTransformer(id);
    }

    // --- EXPORT ONE TRANSFORMER ---
    @GetMapping("/transformer/{id}")
    public TransformerDto exportOne(@PathVariable Long id) {
        return adminService.exportTransformer(id);
    }

    // --- EXPORT RANGE ---
    @GetMapping("/transformers/{from}/{to}")
    public List<TransformerDto> exportRange(@PathVariable Long from,
                                            @PathVariable Long to) {
        return adminService.exportTransformersRange(from, to);
    }

    // --- EXPORT ALL TRANSFORMERS ---
    @GetMapping("/transformers")
    public List<TransformerDto> exportAll() {
        return adminService.exportAllTransformers();
    }

    // --- GET ALL ERRORS ---
    @GetMapping("/alerts")
    public List<AlertDto> getAllErrors() {
        return adminService.getAllErrors();
    }

    // --- GET ONLY CRITICAL ---
    @GetMapping("/alerts/critical")
    public List<AlertDto> getCriticalAlerts() {
        return adminService.getCriticalAlerts();
    }

    // --- EXPORT LOGS ---
    @GetMapping("/logs/{id}")
    public List<String> exportLogs(@PathVariable Long id) {
        return adminService.exportTransformerLogs(id);
    }
}