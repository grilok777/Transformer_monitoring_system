package com.example.controller;

import com.example.dto.AlertDto;
import com.example.entity.mongo.Alert;
import com.example.service.interfaces.AlertService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/alerts")
@AllArgsConstructor
public class AlertController {

    private final AlertService alertService;

    // треба бути створити
    // CreateAlertRequest (DTO)
    // по аналогії з RegisterRequest

    @PostMapping("/create")
    public Alert create(@RequestBody Alert alert) {
        return alertService.create(alert);
    }

    @GetMapping("/active")
    public List<AlertDto> active() {
        return alertService.getActiveAlerts();
    }
}