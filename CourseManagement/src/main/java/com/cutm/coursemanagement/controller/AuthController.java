package com.cutm.coursemanagement.controller;

import com.cutm.coursemanagement.entity.User;
import com.cutm.coursemanagement.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Register
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody User user) {

        String response = authService.register(user);

        if (response.equals("Username already exists")) {
            return ResponseEntity.badRequest()
                    .body(response);
        }

        return ResponseEntity.ok(response);
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody User user) {

        String token = authService.login(user);

        return ResponseEntity.ok(token);
    }
}