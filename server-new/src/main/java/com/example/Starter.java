package com.example;

import com.example.entity.Role;
import com.example.entity.User;
import com.example.models.Admin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;
//@Component
public class Starter implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine();
        String password = scanner.nextLine();
        User user = Admin.builder()
                .email(email)
                .password(password)
                .role(Role.ADMIN)
                .build();

        System.out.println();
    }
}
