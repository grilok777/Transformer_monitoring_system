package com.example.service.impl;

import com.example.config.CustomUserDetails;
import com.example.dto.request.LoginRequest;
import com.example.dto.request.LogoutRequest;
import com.example.dto.request.RefreshTokenRequest;
import com.example.dto.request.RegisterRequest;
import com.example.dto.response.JwtResponse;
import com.example.dto.response.MessageResponse;
import com.example.entity.postgres.RevokedToken;
import com.example.entity.postgres.User;
import com.example.entity.postgres.Role;
import com.example.exception.EmailAlreadyExistException;
import com.example.exception.InvalidPasswordException;
import com.example.exception.InvalidTokenException;
import com.example.exception.UserNotFoundException;
import com.example.mapper.UserMapper;
import com.example.repository.jpa.RevokedTokenRepository;
import com.example.service.interfaces.AuthService;
import com.example.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.repository.jpa.UserRepository;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RevokedTokenRepository revokedTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistException();
        }
        userService.createUser(request, Role.UNDEFINED);
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        User user = userRepository.findUserByEmail(request.email())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        CustomUserDetails userDetails = new CustomUserDetails(user);

        return new JwtResponse(
                jwtService.generateToken(userDetails, TimeUnit.MINUTES.toMillis(15)),
                jwtService.generateToken(userDetails, TimeUnit.DAYS.toMillis(30)),
                UserMapper.fromUserToDto(user)
        );
    }

    @Override
    public MessageResponse logout(LogoutRequest request) {
        String token = request.token();
        if (token == null || token.isBlank()) {
            throw new InvalidTokenException("Token is required for logout");
        }

        if (!revokedTokenRepository.existsByToken(token)) {
            revokedTokenRepository.save(new RevokedToken(token, LocalDateTime.now()));
        }

        return new MessageResponse("User logged out successfully");
    }

    @Override
    public JwtResponse refresh(RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new InvalidTokenException("Refresh token is empty");
        }

        if (revokedTokenRepository.existsByToken(refreshToken)) {
            throw new InvalidTokenException("Refresh token revoked");
        }

        String username = jwtService.extractUsername(refreshToken);

        User user = userRepository.findUserByEmail(username)
                .orElseThrow(UserNotFoundException::new);

        CustomUserDetails userDetails = new CustomUserDetails(user);

        return new JwtResponse(
                jwtService.generateToken(userDetails, TimeUnit.MINUTES.toMillis(15)),
                jwtService.generateToken(userDetails, TimeUnit.DAYS.toMillis(30)),
                UserMapper.fromUserToDto(user)
        );
    }
}