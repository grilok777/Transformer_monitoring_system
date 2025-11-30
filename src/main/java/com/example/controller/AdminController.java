package com.example.controller;

import com.example.exception.TransformerNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PostMapping("/transformer/create")
    public void create(@RequestBody TransformerRequest request) {
        adminService.createTransformer(request);
    }

    @PutMapping("/transformer/update/{id}")
    public void update(@PathVariable("id") Long id,
                       @RequestBody TransformerRequest request) {
        adminService.updateTransformer(id, request);
    }

    @DeleteMapping("/transformer/deactivate/{id}")
    public void deactivateTransformer(@PathVariable("id") Long id) {
        adminService.deactivateTransformer(id);
    }

    @GetMapping("/transformer/export/{id}")
    public ResponseEntity<TransformerDto> exportOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(adminService.exportTransformer(id)
                .orElseThrow(TransformerNotFoundException::new));
    }

    @GetMapping("/transformers/export/{from}/{to}")
    public ResponseEntity<List<TransformerDto>> exportRange(@PathVariable("from") Long from,
                                            @PathVariable("to") Long to) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Logged in user: " + auth.getName());
        System.out.println("Authorities: " + auth.getAuthorities());
        return ResponseEntity.ok(adminService.exportTransformersRange(from, to));
    }

    @GetMapping("/transformers/export/all")
    public ResponseEntity<List<TransformerDto>> exportAll() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Logged in user: " + auth.getName());
        System.out.println("Authorities: " + auth.getAuthorities());
        return ResponseEntity.ok(adminService.exportAllTransformers());
    }

    @GetMapping("/alerts")
    public ResponseEntity<List<AlertDto>> getAllErrors() {
        return ResponseEntity.ok(adminService.getAllErrors());
    }

    @GetMapping("/alerts/critical")
    public ResponseEntity<List<AlertDto>> getCriticalAlerts() {
        return ResponseEntity.ok(adminService.getCriticalAlerts());
    }

    @GetMapping("/logs/export/{id}")
    public ResponseEntity<List<String>> exportLogs(@PathVariable("id") Long id) {
        return ResponseEntity.ok(adminService.exportTransformerLogs(id));
    }
}