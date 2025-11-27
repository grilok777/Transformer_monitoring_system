package com.example.config;

import com.example.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.example.repository.jpa.UserRepository;

@Service
@RequiredArgsConstructor

public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        return new CustomUserDetails(
                userRepository.findUserByEmail(username)
                .orElseThrow(UserNotFoundException::new));
    }
}