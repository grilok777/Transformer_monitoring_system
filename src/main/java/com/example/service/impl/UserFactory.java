package com.example.service.impl;

import com.example.dto.request.RegisterRequest;
import com.example.entity.postgres.Role;
import com.example.entity.postgres.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactory {

    private final PasswordEncoder passwordEncoder;

    public User createUser(Role role, RegisterRequest request) {
        return User.builder()
                .email(request.email())
                .nameUKR(request.nameUKR())
                .password(passwordEncoder.encode(request.password()))
                .role(role != null ? role : Role.UNDEFINED)
                .build();
    }
}