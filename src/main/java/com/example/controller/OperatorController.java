package com.example.controller;

import com.example.dto.AlertDto;
import com.example.dto.TransformerDto;
import com.example.service.interfaces.OperatorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operator") // є питання
//@CrossOrigin(origins = "*")
//@PreAuthorize("hasAnyRole('OPERATOR', 'ADMIN', 'DATA_ANALYST')")
@AllArgsConstructor
public class OperatorController {

    private final OperatorService operatorService;

    @GetMapping("/transformers")
    public List<TransformerDto> getAllTransformersStatus() {
        return operatorService.getAllTransformersStatus();
    }

    @GetMapping("/transformers/{id}")
    public TransformerDto getTransformerStatus(@PathVariable Long id) {//Long
        return operatorService.getTransformerStatus(id);
    }

//    @PostMapping("/transformers/{id}/process-error")
//    public AlertDto processError(
//            @PathVariable Long id//Long
//    ) {
//        return operatorService.addErrorProcessing(id);
//    }

    @GetMapping("/transformers/{id}/alerts")
    public List<AlertDto> getAlerts(@PathVariable Long id) {
        return operatorService.getTransformerAlerts(id);
    }
}