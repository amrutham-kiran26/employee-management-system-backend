package com.kiran.ems.controller;

import com.kiran.ems.dto.CreateUserRequest;
import com.kiran.ems.dto.UpdateUserRequest;
import com.kiran.ems.dto.UserResponse;
import com.kiran.ems.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create User
    @PostMapping
    public UserResponse createUser(
            @Valid @RequestBody CreateUserRequest createUserRequest) {

        return userService.createUser(createUserRequest);
    }

    // Get All Users
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get User By Id
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Update User
    @PutMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest updateUserRequest) {

        return userService.updateUser(id, updateUserRequest);
    }

    // Delete User
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}