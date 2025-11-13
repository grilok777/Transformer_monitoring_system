package com.example.service.interfaces;

import com.example.dto.response.JwtResponse;
import com.example.dto.request.LoginRequest;
import com.example.dto.request.LogoutRequest;
import com.example.dto.request.RegisterRequest;
import com.example.dto.response.MessageResponse;

public interface AuthService {
    JwtResponse login(LoginRequest request);

    void register(RegisterRequest request);

    MessageResponse logout(LogoutRequest request);
}