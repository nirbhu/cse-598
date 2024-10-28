package com.concussion.config;

import com.concussion.model.User;
import com.concussion.model.UserRole;
import com.concussion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create test users if they don't exist
        createUserIfNotExists("athlete1", "password", UserRole.ATHLETE);
        createUserIfNotExists("athlete2", "password", UserRole.ATHLETE);
        createUserIfNotExists("coach1", "password", UserRole.COACH);
        createUserIfNotExists("healthcare1", "password", UserRole.HEALTHCARE_PROFESSIONAL);
    }

    private void createUserIfNotExists(String username, String password, UserRole role) {
        if (userService.findByUsername(username).isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);
            userService.createUser(user);
        }
    }
}
