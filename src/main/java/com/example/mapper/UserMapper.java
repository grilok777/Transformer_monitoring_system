package com.example.mapper;

import com.example.dto.UserDto;
import com.example.entity.postgres.User;

public class UserMapper {
    public static UserDto fromUserToDto(User user){
        if(user == null) throw new NullPointerException();

        return new UserDto(
                user.getId(),
                user.getNameUKR(),
                user.getEmail(),
                user.getRole()
        );
    }
}