package com.example.controller;

import com.example.dto.response.MessageResponse;
import com.example.service.interfaces.DataAnalystService;
import com.example.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analyst")
@RequiredArgsConstructor
public class DataAnalystController {
    //@Qualifier("dataAnalystServiceImpl")
    private final DataAnalystService analystService;
    private final UserService userService;

    @GetMapping("/send")
    public MessageResponse sendReport(){
        return new MessageResponse("");
    }

    @GetMapping("/transformer/{id}")
    public TransformerDto getTransformer(@PathVariable Long id) {
        return analystService.exportTransformer(id);
    }

    @GetMapping("/transformers/{from}/{to}")
    public List<TransformerDto> getTransformersRange(@PathVariable Long from,
                                                     @PathVariable Long to) {
        return analystService.exportTransformersRange(from, to);
    }

    @GetMapping("/transformers")
    public List<TransformerDto> getAll() {
        return analystService.exportAllTransformers();
    }

    @GetMapping("/alerts")
    public List<AlertDto> getAllAlerts() {
        return analystService.getAllErrors();
    }

    @GetMapping("/alerts/critical")
    public List<AlertDto> getCriticalAlerts() {
        return analystService.getCriticalAlerts();
    }

    @GetMapping("/logs/{id}")
    public List<String> getLogs(@PathVariable Long id) {
        return analystService.exportTransformerLogs(id);
    }
}








