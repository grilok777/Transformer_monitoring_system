package com.example;

import com.example.dto.UserDto;
import com.example.dto.request.*;

import com.example.entity.Role;
import com.example.exception.InvalidPasswordException;
import com.example.service.interfaces.AuthService;
import com.example.service.impl.CreatorServiceImpl;
import com.example.service.interfaces.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class Starter implements CommandLineRunner {

    private final UserService userService;
    private final AuthService authService;
    private final CreatorServiceImpl creatorService;
    private String currentToken;
    private final Scanner scanner = new Scanner(System.in);

    public Starter(UserService userService, AuthService authService, CreatorServiceImpl creatorService) {
        this.userService = userService;
        this.authService = authService;
        this.creatorService = creatorService;
    }

    @Override
    public void run(String... args) {
        while (true) {
            showMenu();
            int choice = readInt();

            switch (choice) {
                case 1 -> createUserFromConsole();
                case 2 -> loginUserFromConsole();
                case 3 -> logoutUserFromConsole();
                case 4 -> setNewPass();
                case 5 -> setNewName();
                case 6 -> showUsers();
                case 7 -> setNewRole();
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private void setNewRole() {
        System.out.print("Enter user email: ");
        String email = scanner.nextLine().trim();

        Optional<UserDto> userOpt = userService.getUserByEmail(email);
        if (userOpt.isEmpty()) {
            System.err.println("User not found!");
            return;
        }

        UserDto user = userOpt.get();

        System.out.print("Enter new role (UNDEFINED, ADMIN, etc.): ");
        String newRoleInput = scanner.nextLine().trim().toUpperCase();

        Role newRole;
        try {
            newRole = Role.valueOf(newRoleInput);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid role: " + newRoleInput);
            return;
        }

        try {
            creatorService.changeRoleOfUser(user.id(), newRole);
            System.out.println("Role updated successfully!");
        } catch (RuntimeException e) {
            System.err.println("Failed to update role: " + e.getMessage());
        }
    }

    private void showUsers() {
        creatorService.getAllUsers().forEach(System.out::println);
    }

    private void showMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1. Create user");
        System.out.println("2. Login user");
        System.out.println("3. Logout user");
        System.out.println("4. Change pass");
        System.out.println("5. Change name");
        System.out.println("6. Show users");
        System.out.println("7. Change role");
        System.out.println("0. Exit");
        System.out.print("Your choice: ");
    }

    private int readInt() {
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
        return choice;
    }
    private void createUserFromConsole() {
        System.out.println("\n=== Create User ===");

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Name (UKR): ");
        String nameUKR = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        System.out.print("Do you want to make this user a CREATOR? (yes/no): ");
        String creatorInput = scanner.nextLine().trim().toLowerCase();

        Role role = creatorInput.equals("yes") ? Role.CREATOR : Role.UNDEFINED;

        if (email.isBlank() || nameUKR.isBlank() || password.isBlank()) {
            System.out.println("All fields are required!");
            return;
        }

        RegisterRequest user = new RegisterRequest(nameUKR, email, password);
        userService.createUser(user, role);

        System.out.println("\nUser created successfully!");
        System.out.println("Email: " + user.email());
        System.out.println("Name: " + user.nameUKR());
        System.out.println("Role: " + role);
    }

    private void loginUserFromConsole() {
        System.out.println("\n=== Login User ===");

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        if (email.isBlank() || password.isBlank()) {
            System.out.println("All fields are required!");
            return;
        }

        try {
            var loginResponse = authService.login(new LoginRequest(email, password));
            currentToken = loginResponse.token();
            UserDto userDto = loginResponse.userDto();

            System.out.println("\nUser logged in successfully!");
            System.out.println("Token: " + currentToken);
            System.out.println("User: " + userDto);
        } catch (RuntimeException e) {
            System.err.println("Login failed: " + e.getMessage());
        }
    }

    private void logoutUserFromConsole() {
        if (currentToken == null || currentToken.isBlank()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        try {
            authService.logout(new LogoutRequest(currentToken));
            currentToken = null;
        } catch (RuntimeException e) {
            System.err.println("Logout failed: " + e.getMessage());
        }
    }

    private void setNewPass() {
        System.out.print("Enter user email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter old password: ");
        String oldPassword = scanner.nextLine().trim();

        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine().trim();

        try {
            Optional<UserDto> userOpt = userService.getUserByEmail(email);
            if (userOpt.isPresent()) {
                Long userId = userOpt.get().id();
                userService.changePassword(new ChangePasswordRequest(userId, oldPassword, newPassword));
                System.out.println("Password updated successfully!");
            }
        } catch (InvalidPasswordException e) {
            System.err.println("Failed to update password: " + e.getMessage());
        }
    }

    private void setNewName() {
        System.out.print("Enter user email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter new name: ");
        String newName = scanner.nextLine().trim();

        try {
            Optional<UserDto> userOpt = userService.getUserByEmail(email);
            if (userOpt.isPresent()) {
                Long userId = userOpt.get().id();
                userService.changeName(new ChangeNameRequest(userId, newName));
                System.out.println("name updated successfully!");
            }
            System.out.println("Name updated successfully!");
        } catch (RuntimeException e) {
            System.err.println("Failed to update name: " + e.getMessage());
        }
    }
}