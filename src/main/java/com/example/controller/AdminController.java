package com.example.controller;

import com.example.exception.TransformerNotFoundException;
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
@PreAuthorize("hasAuthority('ADMIN')")
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PutMapping("/transformer/{id}")
    public void update(@PathVariable Long id,
                                 @RequestBody TransformerRequest request) {
        adminService.updateTransformer(id, request);
    }

    @DeleteMapping("/transformer/{id}")
    public void deactivateTransformer(@PathVariable Long id) {
        adminService.deactivateTransformer(id);
    }

    @GetMapping("/transformer/{id}")
    public TransformerDto exportOne(@PathVariable Long id) {
        return adminService.exportTransformer(id)
                .orElseThrow(TransformerNotFoundException::new);
    }

    @GetMapping("/transformers/{from}/{to}")
    public List<TransformerDto> exportRange(@PathVariable Long from,
                                            @PathVariable Long to) {
        return adminService.exportTransformersRange(from, to);
    }

    @GetMapping("/transformers")
    public List<TransformerDto> exportAll() {
        return adminService.exportAllTransformers();
    }

    @GetMapping("/alerts")
    public List<AlertDto> getAllErrors() {
        return adminService.getAllErrors();
    }

    @GetMapping("/alerts/critical")
    public List<AlertDto> getCriticalAlerts() {
        return adminService.getCriticalAlerts();
    }

    @GetMapping("/logs/{id}")
    public List<String> exportLogs(@PathVariable Long id) {
        return adminService.exportTransformerLogs(id);
    }
}