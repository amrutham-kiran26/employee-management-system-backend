package com.kiran.ems.mapper;

import com.kiran.ems.dto.CreateUserRequest;
import com.kiran.ems.dto.UpdateUserRequest;
import com.kiran.ems.dto.UserResponse;
import com.kiran.ems.entity.User;

public class UserMapper {

    // Convert Request DTO to Entity
    public static User toEntity(CreateUserRequest request) {

        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .status(request.getStatus())
                .build();
    }

    // Convert Entity to Response DTO
    public static UserResponse toResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    // Update Existing Entity
    public static void updateEntity(User user, UpdateUserRequest request) {

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
//        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setStatus(request.getStatus());
    }

}