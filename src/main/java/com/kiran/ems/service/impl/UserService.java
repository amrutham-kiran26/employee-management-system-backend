package com.kiran.ems.service;

import com.kiran.ems.dto.CreateUserRequest;
import com.kiran.ems.dto.UpdateUserRequest;
import com.kiran.ems.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(CreateUserRequest createUserRequest);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, UpdateUserRequest updateUserRequest);

    void deleteUser(Long id);
}