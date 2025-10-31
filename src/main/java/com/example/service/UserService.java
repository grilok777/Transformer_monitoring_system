package com.example.service;

import com.example.entity.Role;
import com.example.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email);
    List<User> getAll();
    List<User> getUsersByRole(Role role);
    User createUser(User user, Role role);
}
