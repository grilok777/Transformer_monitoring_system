package com.example.dto.request;

public record LoginRequest(
        String email,
        String password
        ) {}