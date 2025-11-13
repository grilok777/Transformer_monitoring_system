package com.example.service.interfaces;

import com.example.dto.UserDto;
import com.example.entity.Role;

import java.util.List;
import java.util.Optional;

public interface CreatorService {
    List<UserDto> getAllUsers();

    List<UserDto> getUsersByRole(Role role);

    Optional<UserDto> getUserByEmail(String email);

    void changeRoleOfUser(Long id, Role role);
}
