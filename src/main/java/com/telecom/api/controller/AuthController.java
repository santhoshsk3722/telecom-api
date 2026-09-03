package com.telecom.api.controller;

import com.telecom.api.dto.ApiResponse;
import com.telecom.api.dto.AuthRequest;
import com.telecom.api.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(
            @RequestBody AuthRequest request) {

        String token = authService.login(
                request.getUsername(),
                request.getPassword()
        );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Login successful",
                        token
                )
        );
    }
}