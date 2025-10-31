package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
//@Table(name = "users")
public abstract class User {
    @Id
    private Long id;
    @Column
    private String nameUKR;
    @Column
    private String email;
    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    public User() {

    }
}
