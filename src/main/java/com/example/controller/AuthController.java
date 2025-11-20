package com.example.controller;

import com.example.dto.*;
import com.example.dto.request.LoginRequest;
import com.example.dto.request.LogoutRequest;
import com.example.dto.request.RegisterRequest;
import com.example.dto.response.JwtResponse;
import com.example.dto.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.service.interfaces.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class AuthController {
    private final AuthService authService;

    @GetMapping("/ping")
    public ResponseEntity<MessageResponse> ping() {
        System.out.println("pong");
        return ResponseEntity.ok(new MessageResponse("pong"));
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        System.out.println("Registration successful");
        return ResponseEntity.ok(new MessageResponse("Registration successful"));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        var loginResponse = authService.login(request);
        String token = loginResponse.token();
        UserDto userDto = loginResponse.userDto();

        System.out.println(token + "\n" + userDto.email() + userDto.nameUKR() + userDto.role() + userDto.id());
        return ResponseEntity.ok(new JwtResponse(token, userDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(@RequestBody LogoutRequest request) {
        System.out.println("User logged out successfully");
        return ResponseEntity.ok(authService.logout(request));
    }
}