package com.example.controller;

import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;
import com.example.entity.mongo.TransformerStatus;
import com.example.service.interfaces.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/operator")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('OPERATOR')")
@RequiredArgsConstructor
public class OperatorController {

    private final OperatorService operatorService;

    @GetMapping("/transformers")
    public ResponseEntity<List<TransformerDto>> getAllTransformers() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Logged in user: " + auth.getName());
        System.out.println("Authorities: " + auth.getAuthorities());
        return ResponseEntity.ok(operatorService.getAllTransformers());
    }

    /*@GetMapping("/transformers-status")
    public ResponseEntity<List<TransformerStatus>> getAllTransformersStatus() {
        return ResponseEntity.ok(operatorService.getAllTransformersStatus());
    }*/

    @GetMapping("/transformers/{id}")
    public ResponseEntity<Optional<TransformerDto>> getTransformer(@PathVariable("id") Long id) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Logged in user: " + auth.getName());
        System.out.println("Authorities: " + auth.getAuthorities());
        return ResponseEntity.ok(operatorService.getTransformer(id));
    }

    @GetMapping("/transformers/{id}/alerts")
    public ResponseEntity<List<AlertDto>> getAlerts(@PathVariable("id") Long id) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Logged in user: " + auth.getName());
        System.out.println("Authorities: " + auth.getAuthorities());
        return ResponseEntity.ok(operatorService.getTransformerAlerts(id));
    }

    @PostMapping("/transformers/{id}/process-error")
    public ResponseEntity<AlertDto> processError(@PathVariable("id") Long id) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Logged in user: " + auth.getName());
        System.out.println("Authorities: " + auth.getAuthorities());
        return ResponseEntity.ok(operatorService.addErrorProcessing(id));
    }
}