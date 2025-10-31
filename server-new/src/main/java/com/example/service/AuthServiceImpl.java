package com.example.service;

import com.example.config.CustomUserDetails;
import com.example.dto.LoginRequest;
import com.example.dto.LogoutRequest;
import com.example.dto.RegisterRequest;
import com.example.entity.User;
import com.example.entity.Role;
import lombok.RequiredArgsConstructor;
import com.example.models.UndefinedUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }

        User user = UndefinedUser.builder()
                .nameUKR(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.UNDEFINED)
                .build();

        userRepository.save(user);
        return "Operator registered successfully";
    }

    @Override
    public String logout(LogoutRequest request) {
        return "";
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtService.generateToken(new CustomUserDetails(user));
    }
}
