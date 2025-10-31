package com.example.service;

import com.example.entity.Role;
import com.example.entity.User;
import com.example.models.UndefinedUser;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserFactory userFactory;

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.findUsersByRole(role);
    }
    @Override
    @Transactional
    public User createUser(User user, Role role) {
        User userCreate = userFactory.createUser(
                role,
                UndefinedUser
                        .builder()
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .role(role)
                        .build(),
                true
        );
        return userRepository.save(userCreate);
    }
}
