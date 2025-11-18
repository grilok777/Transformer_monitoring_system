package com.example.controller;

import com.example.model.Alert;
import com.example.service.impl.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    private AlertService service;

    @PostMapping
    public Alert create(@RequestBody Alert alert) {
        return service.create(alert);
    }

    @GetMapping("/active")
    public List<Alert> active() {
        return service.getActiveAlerts();
    }
}
