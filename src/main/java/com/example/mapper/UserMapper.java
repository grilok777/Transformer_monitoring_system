package com.example.mapper;

import com.example.dto.UserDto;
import com.example.entity.User;

public class UserMapper {
    public static UserDto fromUserToDto(User user){
        return new UserDto(
                user.getId(),
                user.getNameUKR(),
                user.getEmail(),
                user.getRole()
        );
    }

    public static User fromDtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.id())
                .nameUKR(userDto.nameUKR())
                .email(userDto.email())
                .role(userDto.role())
                .build();
    }
}