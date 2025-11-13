package com.example.dto.request;

public record ChangePasswordRequest(
        Long userId,
        String oldPassword,
        String newPassword
) {}