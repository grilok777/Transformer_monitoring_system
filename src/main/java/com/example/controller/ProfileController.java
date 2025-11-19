package com.example.controller;

import com.example.config.CustomUserDetails;
import com.example.dto.UserDto;
import com.example.dto.request.ChangeNameRequest;
import com.example.dto.request.ChangePasswordRequest;
import com.example.dto.response.MessageResponse;
import com.example.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class ProfileController{

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getProfile(@AuthenticationPrincipal CustomUserDetails user) {
        return userService.getUserByEmail(user.getUsername())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/name")
    public ResponseEntity<MessageResponse> changeName(
            @RequestBody ChangeNameRequest request) {

        userService.changeName(request);
        return ResponseEntity.ok(new MessageResponse("Name updated successfully"));
    }

    @PutMapping("/password")
    public ResponseEntity<MessageResponse> changePassword(
            @RequestBody ChangePasswordRequest request) {

        userService.changePassword(request);
        return ResponseEntity.ok(new MessageResponse("Password updated successfully"));
    }
}