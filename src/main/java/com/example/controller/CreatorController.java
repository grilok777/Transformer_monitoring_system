package com.example.controller;

import com.example.dto.UserDto;
import com.example.dto.response.MessageResponse;
import com.example.entity.postgres.Role;
import com.example.service.interfaces.CreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/creator")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('CREATOR')")

public class CreatorController {

    private final CreatorService creatorService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Logged in user: " + auth.getName());
        System.out.println("Authorities: " + auth.getAuthorities());
        return ResponseEntity.ok(creatorService.getAllUsers());
    }

    @GetMapping("/users/by-role")
    public ResponseEntity<List<UserDto>> getUsersByRole(@RequestParam("role") Role role) {
        return ResponseEntity.ok(creatorService.getUsersByRole(role));
    }

    @GetMapping("/users/by-email")
    public ResponseEntity<Optional<UserDto>> getUserByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(creatorService.getUserByEmail(email));
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<MessageResponse> changeUserRole(
            @PathVariable("id") Long id,
            @RequestParam("role") Role role) {
        creatorService.changeRoleOfUser(id, role);
        return ResponseEntity.ok(new MessageResponse("Role updated successfully"));
    }
}