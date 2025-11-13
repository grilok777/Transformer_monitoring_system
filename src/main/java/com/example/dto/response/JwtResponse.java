package com.example.dto.response;

import com.example.dto.UserDto;

public record JwtResponse (String token, UserDto userDto){}