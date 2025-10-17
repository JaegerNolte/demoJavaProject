package com.project.demo.controller;


import com.project.demo.model.User;
import com.project.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        boolean success = userService.login(user.getUsername(), user.getPassword());
        return success ? "Login Successful!" : "Invalid credentials";
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {

        Long id = userService.register(user);
        return id > 0 ? "User registered with ID" + id : "Registration failed.";
    }

}
