package com.kiran.ems.controller;

import com.kiran.ems.dto.LoginRequest;
import com.kiran.ems.dto.LoginResponse;
import com.kiran.ems.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {

        System.out.println("Login API Hit");

        return authService.login(loginRequest);

    }

}