package com.example.controller;

import com.example.service.interfaces.DataAnalystService;
import com.example.service.interfaces.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DataAnalystController {
    private final DataAnalystService dataAnalystService;
    private final UserService userService;

    public String makeReport(){
        return ":";
    }
}