package com.example.dto;

import com.example.entity.postgres.Role;

public record UserDto(Long id,
                      String nameUKR,
                      String email,
                      Role role
                      ) {
    @Override
    public String toString(){
        return "{id}=" + id + " {nameUKR}=" + nameUKR + " {email}=" + email + " {role}" + role.name()
                ;
    }
}