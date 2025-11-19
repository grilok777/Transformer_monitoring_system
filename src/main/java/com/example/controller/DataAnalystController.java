package com.example.controller;

import com.example.dto.response.MessageResponse;
import com.example.service.interfaces.DataAnalystService;
import com.example.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/reports")
public class DataAnalystController {
    private final DataAnalystService dataAnalystService;
    private final UserService userService;

    @GetMapping("/send")
    public MessageResponse sendReport(){
        return new MessageResponse("");
    }
}