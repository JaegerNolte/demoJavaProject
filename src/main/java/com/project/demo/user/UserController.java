package com.project.demo.user;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

// Handles HTTP requests and responses

@RestController
@RequestMapping("/api/app_users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register") // creates a new user
    public ResponseEntity<String> register(@RequestParam String username,
                                           @RequestParam String password,
                                           @RequestParam String email) {

        userService.registerUser(username, password, email);
        return ResponseEntity.ok("User registered successfully");

    }

    @PostMapping("/login") // validates credentials and returns user info
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password) {

        Optional<User> user = userService.login(username, password);
        if (user.isPresent()) {

            return ResponseEntity.ok(user.get());

        }

        return ResponseEntity.status(401).body("Invalid credentials");

    }


    @PutMapping("/{id}/email") // Updates only the email
    public ResponseEntity<String> updateEmail(@PathVariable int id,
                                              @RequestParam String newEmail) {
        boolean updated = userService.updateEmail(id, newEmail);
        if (updated) return ResponseEntity.ok("Email updated successfully");
        return ResponseEntity.badRequest().body("Failed to update email");
    }

    @PutMapping("/{id}/password") // Verifies old password and updates securely
    public ResponseEntity<String> updatePassword(@PathVariable int id,
                                                 @RequestParam String oldPassword,
                                                 @RequestParam String newPassword) {
        boolean updated = userService.updatePassword(id, oldPassword, newPassword);
        if (updated) return ResponseEntity.ok("Password updated successfully");
        return ResponseEntity.status(403).body("Old password incorrect or user not found");
    }

    @GetMapping // With "/api/app_users" list all users - will be admin only later
    public ResponseEntity<?> listAllUsers() {
        return ResponseEntity.ok(userService.listAllUsers());
    }

}
