package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevokedToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private LocalDateTime revokedAt;

    public RevokedToken(String token, LocalDateTime revokedAt) {
        this.token = token;
        this.revokedAt = revokedAt;
    }
}