package com.example.service.impl;

import com.example.dto.UserDto;
import com.example.entity.postgres.Role;
import com.example.entity.postgres.User;
import com.example.exception.UserNotFoundException;
import com.example.mapper.UserMapper;
import com.example.repository.jpa.UserRepository;
import com.example.service.interfaces.CreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreatorServiceImpl implements CreatorService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::fromUserToDto)
                .toList();
    }

    @Override
    public List<UserDto> getUsersByRole(Role role) {
        return userRepository.findAllByRoleNot(role)
                .stream()
                .map(UserMapper::fromUserToDto)
                .toList();
    }

    @Override
    public Optional<UserDto> getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        return Optional.of(UserMapper.fromUserToDto(user));
    }

    @Override
    public void changeRoleOfUser(Long id, Role role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
        user.setRole(role);
        userRepository.save(user);
    }
}