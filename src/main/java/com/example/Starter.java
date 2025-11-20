package com.example;

import com.example.dto.TransformerDto;
import com.example.dto.UserDto;
import com.example.dto.request.*;

import com.example.entity.Role;
import com.example.exception.InvalidPasswordException;
import com.example.model.Transformer;
import com.example.service.impl.AdminServiceImpl;
import com.example.service.impl.OperatorServiceImpl;
import com.example.service.interfaces.AuthService;
import com.example.service.impl.CreatorServiceImpl;
import com.example.service.impl.DataAnalystServiceImpl;

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
    private final OperatorServiceImpl operatorService;
    private final DataAnalystServiceImpl dataAnalystService;
    private final AdminServiceImpl adminService;

    public Starter(UserService userService, AuthService authService, CreatorServiceImpl creatorService, OperatorServiceImpl operatorService, DataAnalystServiceImpl dataAnalystService, AdminServiceImpl adminService) {
        this.userService = userService;
        this.authService = authService;
        this.creatorService = creatorService;
        this.operatorService = operatorService;
        this.dataAnalystService = dataAnalystService;
        this.adminService = adminService;
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

                case 8 -> showAllTransformers();
                case 9 -> showTransformerById();
                case 10 -> showTransformerAlerts();
                case 11 -> processTransformerError();

                case 12 -> exportSingleTransformer();
                case 13 -> exportTransformersRange();
                case 14 -> exportAllTransformers();
                case 15 -> viewAllErrors();
                case 16 -> viewCriticalAlerts();
                case 17 -> exportLogs();

                case 18 -> adminCreateTransformer();
                case 19 -> adminUpdateTransformer();
                case 20 -> adminDeactivateTransformer();
                case 21 -> adminExportTransformer();
                case 22 -> adminExportRange();
                case 23 -> adminExportAll();
                case 24 -> adminShowAllErrors();
                case 25 -> adminShowCriticalErrors();
                case 26 -> adminExportLogs();
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

        System.out.println("Operator");
        System.out.println("8. Show all transformers");
        System.out.println("9. Show transformer by ID");
        System.out.println("10. Show transformer alerts");
        System.out.println("11. Process transformer error");

        System.out.println("Data analyst");
        System.out.println("12. Export transformer");
        System.out.println("13. Export transformers range");
        System.out.println("14. Export all transformers");
        System.out.println("15. View all errors");
        System.out.println("16. View critical alerts");
        System.out.println("17. Export logs");

        System.out.println("Admin");
        System.out.println("18. Create transformer");
        System.out.println("19. Update transformer");
        System.out.println("20. Deactivate transformer");
        System.out.println("21. Export transformer by ID");
        System.out.println("22. Export transformer range (FROM..TO)");
        System.out.println("23. Export all transformers");
        System.out.println("24. View all errors");
        System.out.println("25. View critical alerts");
        System.out.println("26. Export transformer logs");

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

    private void showAllTransformers() {
        var list = operatorService.getAllTransformersStatus();
        if (list.isEmpty()) {
            System.out.println("No transformers found.");
            return;
        }
        list.forEach(t -> System.out.println(t.toString()));
    }

    private void showTransformerById() {
        System.out.print("Enter transformer ID: ");
        String id = scanner.nextLine().trim();

        try {
            TransformerDto t = operatorService.getTransformerStatus(Long.valueOf(id));//Long.valueOf(id)
            System.out.println(t);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
    private void showTransformerAlerts() {
        System.out.print("Enter transformer ID: ");
        String id = scanner.nextLine().trim();
        try {
            var alerts = operatorService.getTransformerAlerts(Long.valueOf(id));//Long.valueOf(id)

            if (alerts.isEmpty()) {
                System.out.println("No alerts for this transformer.");
                return;
            }

            alerts.forEach(System.out::println);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    private void processTransformerError() {
        System.out.print("Enter alert ID: ");
        String id = scanner.nextLine().trim();
        try {
            operatorService.addErrorProcessing(Long.valueOf(id));//Long.valueOf(id)
            System.out.println("Error processed.");
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    private void exportSingleTransformer() {
        System.out.print("Enter transformer ID: ");
        Long id = Long.valueOf(scanner.nextLine().trim());

        try {
            TransformerDto dto = dataAnalystService.exportTransformer(id);
            System.out.println(dto);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    private void exportTransformersRange() {
        System.out.print("From ID: ");
        Long from = Long.valueOf(scanner.nextLine().trim());

        System.out.print("To ID: ");
        Long to = Long.valueOf(scanner.nextLine().trim());

        var list = dataAnalystService.exportTransformersRange(from, to);

        if (list.isEmpty()) {
            System.out.println("No transformers found.");
        }

        list.forEach(System.out::println);
    }

    private void exportAllTransformers() {
        var list = dataAnalystService.exportAllTransformers();

        if (list.isEmpty()) {
            System.out.println("No transformers found.");
            return;
        }

        list.forEach(System.out::println);
    }

    private void viewAllErrors() {
        var alerts = dataAnalystService.getAllErrors();

        if (alerts.isEmpty()) {
            System.out.println("No alerts found.");
            return;
        }

        alerts.forEach(System.out::println);
    }

    private void viewCriticalAlerts() {
        var alerts = dataAnalystService.getCriticalAlerts();

        if (alerts.isEmpty()) {
            System.out.println("No critical alerts found.");
            return;
        }

        alerts.forEach(System.out::println);
    }

    private void exportLogs() {
        System.out.print("Enter transformer ID: ");
        Long id = Long.valueOf(scanner.nextLine().trim());

        var logs = dataAnalystService.exportTransformerLogs(id);

        if (logs.isEmpty()) {
            System.out.println("No logs found.");
            return;
        }

        logs.forEach(System.out::println);
    }

    private void adminCreateTransformer() {
        System.out.println("\n=== CREATE TRANSFORMER ===");

        System.out.print("Manufacturer: ");
        String manufacturer = scanner.nextLine();

        System.out.print("Model Type: ");
        String modelType = scanner.nextLine();

        System.out.print("Rated Power (kVA): ");
        Double ratedPowerKVA = Double.valueOf(scanner.nextLine());

        System.out.print("Primary Voltage (kV): ");
        Integer primaryKV = Integer.valueOf(scanner.nextLine());

        System.out.print("Secondary Voltage (kV): ");
        Integer secondaryKV = Integer.valueOf(scanner.nextLine());

        System.out.print("Frequency (Hz): ");
        Double frequencyHz = Double.valueOf(scanner.nextLine());

        TransformerRequest request = new TransformerRequest(
                manufacturer,
                modelType,
                ratedPowerKVA,
                primaryKV,
                secondaryKV,
                frequencyHz,
                Boolean.TRUE,
                Boolean.FALSE
        );

        TransformerDto dto = adminService.createTransformer(request);

        System.out.println("\nTransformer created:");
        System.out.println(dto);
    }

    private void adminUpdateTransformer() {
        System.out.print("Enter transformer ID: ");
        Long id = Long.valueOf(scanner.nextLine());

        System.out.print("New Manufacturer: ");
        String manufacturer = scanner.nextLine();

        System.out.print("New Model Type: ");
        String modelType = scanner.nextLine();

        System.out.print("New Rated Power (kVA): ");
        Double ratedPowerKVA = Double.valueOf(scanner.nextLine());

        System.out.print("New Primary Voltage (kV): ");
        Integer primaryKV = Integer.valueOf(scanner.nextLine());

        System.out.print("New Secondary Voltage (kV): ");
        Integer secondaryKV = Integer.valueOf(scanner.nextLine());

        System.out.print("New Frequency (Hz): ");
        Double frequencyHz = Double.valueOf(scanner.nextLine());

        TransformerRequest request = new TransformerRequest(
                manufacturer,
                modelType,
                ratedPowerKVA,
                primaryKV,
                secondaryKV,
                frequencyHz,
                Boolean.TRUE,
                Boolean.FALSE
        );

        TransformerDto dto = adminService.updateTransformer(id, request);

        System.out.println("\nUpdated transformer:");
        System.out.println(dto);
    }

    private void adminDeactivateTransformer() {
        System.out.print("Enter transformer ID: ");
        Long id = Long.valueOf(scanner.nextLine());

        adminService.deactivateTransformer(id);
        System.out.println("Transformer deactivated.");
    }

    private void adminExportTransformer() {
        System.out.print("Enter transformer ID: ");
        Long id = Long.valueOf(scanner.nextLine());

        System.out.println(adminService.exportTransformer(id));
    }

    private void adminExportRange() {
        System.out.print("From ID: ");
        Long from = Long.valueOf(scanner.nextLine());

        System.out.print("To ID: ");
        Long to = Long.valueOf(scanner.nextLine());

        adminService.exportTransformersRange(from, to)
                .forEach(System.out::println);
    }

    private void adminExportAll() {
        adminService.exportAllTransformers()
                .forEach(System.out::println);
    }

    private void adminShowAllErrors() {
        adminService.getAllErrors()
                .forEach(System.out::println);
    }
    private void adminShowCriticalErrors() {
        adminService.getCriticalAlerts()
                .forEach(System.out::println);
    }

    private void adminExportLogs() {
        System.out.print("Enter transformer ID: ");
        Long id = Long.valueOf(scanner.nextLine());

        adminService.exportTransformerLogs(id)
                .forEach(System.out::println);
    }
}