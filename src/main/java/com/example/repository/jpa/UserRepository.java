package com.example.repository.jpa;

import com.example.entity.postgres.Role;
import com.example.entity.postgres.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    Boolean existsByEmail(String email);

    List<User> findAllByRoleNot(Role role);
}