package com.example.service;

import com.example.dto.LoginRequest;
import com.example.dto.LogoutRequest;
import com.example.dto.RegisterRequest;


//public interface AuthService {
//    String login(String email, String password);
//    void register(String email, String password, Role role);
//    }

public interface AuthService {
    String login(LoginRequest request);
    String register(RegisterRequest request);
    String logout(LogoutRequest request);
}