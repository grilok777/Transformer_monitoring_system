package com.example.service.impl;

import com.example.config.CustomUserDetails;
import com.example.dto.*;
import com.example.dto.request.LoginRequest;
import com.example.dto.request.LogoutRequest;
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
import java.util.Optional;

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
    public MessageResponse logout(LogoutRequest request) {
        String token = request.token();
        if (token == null || token.isBlank()) {
            throw new InvalidTokenException("Token is required for logout");
        }

        if (revokedTokenRepository.existsByToken(token)) {
            return new MessageResponse("Token already revoked");
        }

        revokedTokenRepository.save(new RevokedToken(token, LocalDateTime.now()));
        return new MessageResponse("User logged out successfully");
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        User user = userRepository.findUserByEmail(request.email())
                .orElseThrow(UserNotFoundException::new);

        Optional.of(request.password())
                .filter(p -> passwordEncoder.matches(p, user.getPassword()))
                .orElseThrow(InvalidPasswordException::new);

        String token = jwtService.generateToken(new CustomUserDetails(user));
        UserDto userDto = UserMapper.fromUserToDto(user);
        return new JwtResponse(token, userDto);
    }
}