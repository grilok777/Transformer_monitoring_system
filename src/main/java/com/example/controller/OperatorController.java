package com.example.controller;

import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;
import com.example.model.Alert;
import com.example.model.Transformer;
import com.example.service.interfaces.OperatorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operator")
public class OperatorController {

    private final OperatorService operatorService;

    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    @GetMapping("/transformers")
    public List<TransformerDto> getAllTransformersStatus() {
        return operatorService.getAllTransformersStatus();
    }

    @GetMapping("/transformers/{id}")
    public TransformerDto getTransformerStatus(@PathVariable Long id) {//Long
        return operatorService.getTransformerStatus(id);
    }

    @PostMapping("/transformers/{id}/process-error")
    public Alert processError(
            @PathVariable Long id//Long
    ) {
        return operatorService.addErrorProcessing(id);
    }

    @GetMapping("/transformers/{id}/alerts")
    public List<AlertDto> getAlerts(@PathVariable Long id) {//Long
        return operatorService.getTransformerAlerts(id);
    }
}