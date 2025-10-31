package com.example.service;

import com.example.entity.User;
import com.example.entity.Role;
import com.example.models.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
public class UserFactory {

    private final PasswordEncoder passwordEncoder;

    private final Map<Role, Function<User, User>> roleBuilders;

    public UserFactory(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

        roleBuilders = Map.of(
                Role.ADMIN, user -> Admin.builder()
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .role(Role.ADMIN)
                        .build(),

                Role.OPERATOR, user -> Operator.builder()
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .role(Role.OPERATOR)
                        .build(),

                Role.CREATOR, user -> Creator.builder()
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .role(Role.CREATOR)
                        .build(),

                Role.DATA_ANALYST, user -> DataAnalyst.builder()
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .role(Role.DATA_ANALYST)
                        .build(),

                Role.UNDEFINED, user -> UndefinedUser.builder()
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .role(Role.UNDEFINED)
                        .build()
        );
    }

    public User createUser(Role role, User user, boolean encodePassword) {
        Function<User, User> builder = roleBuilders.get(role);
        if (builder == null) {
            throw new IllegalArgumentException("Unknown role: " + role);
        }

        User newUser = builder.apply(user);

        if (encodePassword && newUser.getPassword() != null) {
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }

        return newUser;
    }
}
