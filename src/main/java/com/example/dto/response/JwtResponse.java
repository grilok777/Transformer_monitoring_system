package com.example.dto.response;

import com.example.dto.UserDto;

public record JwtResponse(
        String accessToken,
        String refreshToken,
        UserDto userDto) {}