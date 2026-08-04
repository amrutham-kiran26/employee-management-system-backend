package com.kiran.ems.service.impl;

import com.kiran.ems.dto.LoginRequest;
import com.kiran.ems.dto.LoginResponse;
import com.kiran.ems.entity.User;
import com.kiran.ems.exception.BadRequestException;
import com.kiran.ems.exception.ResourceNotFoundException;
import com.kiran.ems.repository.UserRepository;
import com.kiran.ems.security.CustomUserDetailsService;
import com.kiran.ems.security.JwtService;
import com.kiran.ems.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetails;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final CustomUserDetailsService customUserDetailsService;

    public AuthServiceImpl(
            UserRepository userRepository,
            JwtService jwtService,
            CustomUserDetailsService customUserDetailsService) {

        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        logger.info("Login attempt for email: {}", loginRequest.getEmail());

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> {

                    logger.error("User not found with email: {}", loginRequest.getEmail());

                    return new ResourceNotFoundException(
                            "User not found with email: " + loginRequest.getEmail());
                });

        if (!user.getStatus().equalsIgnoreCase("ACTIVE")) {

            logger.warn("Inactive user login attempt: {}", user.getEmail());

            throw new BadRequestException("User account is inactive.");
        }

        if (!user.getPassword().equals(loginRequest.getPassword())) {

            logger.warn("Invalid password for email: {}", loginRequest.getEmail());

            throw new BadRequestException("Invalid email or password.");
        }

        logger.info("Login successful for email: {}", user.getEmail());

        UserDetails userDetails =
                customUserDetailsService.loadUserByUsername(user.getEmail());

        String token =
                jwtService.generateToken(userDetails);

        return LoginResponse.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .message("Login Successful")
                .build();
    }
}