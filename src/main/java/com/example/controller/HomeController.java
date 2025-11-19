package com.example.controller;

import com.example.config.CustomUserDetails;
import com.example.dto.UserDto;
import com.example.dto.response.MessageResponse;
import com.example.entity.postgres.Role;
import com.example.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class HomeController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<MessageResponse> getHomePage(@AuthenticationPrincipal CustomUserDetails user) {
        if (user == null) {
            return ResponseEntity.status(401).body(new MessageResponse("Unauthorized"));
        }

        Role role = userService.getUserByEmail(user.getUsername())
                .map(UserDto::role)
                .orElse(Role.UNDEFINED);

        return ResponseEntity.ok(new MessageResponse(
                switch (role) {
                    case ADMIN -> "ADMIN_HOME";
                    case CREATOR -> "CREATOR_HOME";
                    case DATA_ANALYST -> "ANALYST_HOME";
                    default -> "USER_HOME";
                }));
    }
}