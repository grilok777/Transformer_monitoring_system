package com.example.service.impl;

import com.example.dto.UserDto;
import com.example.dto.request.ChangeNameRequest;
import com.example.dto.request.ChangePasswordRequest;
import com.example.dto.request.RegisterRequest;
import com.example.entity.postgres.Role;
import com.example.entity.postgres.User;
import com.example.exception.InvalidPasswordException;
import com.example.exception.UserNotFoundException;
import com.example.mapper.UserMapper;
import com.example.repository.jpa.UserRepository;
import com.example.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUser(RegisterRequest request, Role role) {
        User newUser = userFactory.createUser(role != null ? role : Role.UNDEFINED, request);
        userRepository.save(newUser);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid old password");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void changeName(ChangeNameRequest request) {
        User user = userRepository.findById(request.id())
                .orElseThrow(UserNotFoundException::new);
        user.setNameUKR(request.name());
        userRepository.save(user);
    }

    @Override
    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .map(UserMapper::fromUserToDto);
    }
}