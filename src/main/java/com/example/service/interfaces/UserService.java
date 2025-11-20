package com.example.service.interfaces;

import com.example.dto.UserDto;
import com.example.dto.request.ChangeNameRequest;
import com.example.dto.request.ChangePasswordRequest;
import com.example.dto.request.RegisterRequest;
import com.example.entity.postgres.Role;

import java.util.Optional;


public interface UserService {
    void createUser(RegisterRequest request, Role role);

    void changePassword(ChangePasswordRequest request);

    void changeName(ChangeNameRequest request);

    Optional<UserDto> getUserByEmail(String email);
}