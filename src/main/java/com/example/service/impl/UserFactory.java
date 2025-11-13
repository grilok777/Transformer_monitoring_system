package com.example.service.impl;

import com.example.dto.request.RegisterRequest;
import com.example.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactory {

    private final PasswordEncoder passwordEncoder;

    public User createUser(Role role, RegisterRequest request) {
        if (role == null) {
            role = Role.UNDEFINED;
        }

        User user = new User();
        user.setEmail(request.email());
        user.setNameUKR(request.nameUKR());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(role);
        return user;
    }
}