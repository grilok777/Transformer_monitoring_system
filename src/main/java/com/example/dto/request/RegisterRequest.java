package com.example.dto.request;

public record RegisterRequest(
        String nameUKR,
        String email,
        String password
) {}