package com.kiran.ems.service.impl;

import com.kiran.ems.dto.CreateUserRequest;
import com.kiran.ems.dto.UpdateUserRequest;
import com.kiran.ems.dto.UserResponse;
import com.kiran.ems.entity.User;
import com.kiran.ems.exception.BadRequestException;
import com.kiran.ems.exception.ResourceNotFoundException;
import com.kiran.ems.mapper.UserMapper;
import com.kiran.ems.repository.UserRepository;
import com.kiran.ems.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse createUser(CreateUserRequest createUserRequest) {

        logger.info("Creating user with email: {}", createUserRequest.getEmail());

        // Check duplicate email
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {

            logger.warn("Email already exists: {}", createUserRequest.getEmail());

            throw new BadRequestException(
                    "Email already exists: " + createUserRequest.getEmail());
        }

        // Convert DTO to Entity
        User user = UserMapper.toEntity(createUserRequest);

        // Encrypt Password
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );


        // Save User
        User savedUser = userRepository.save(user);

        logger.info("User created successfully with ID: {}", savedUser.getId());

        // Convert Entity to Response DTO
        return UserMapper.toResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        logger.info("Fetching all users.");

        List<User> users = userRepository.findAll();

        logger.info("Total users found: {}", users.size());

        return users.stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {

        logger.info("Fetching user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {

                    logger.error("User not found with ID: {}", id);

                    return new ResourceNotFoundException(
                            "User not found with ID: " + id);
                });

        logger.info("User fetched successfully with ID: {}", id);

        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest updateUserRequest) {

        logger.info("Updating user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {

                    logger.error("User not found with ID: {}", id);

                    return new ResourceNotFoundException(
                            "User not found with ID: " + id);
                });

        // Check duplicate email only if email is changed
        if (!user.getEmail().equals(updateUserRequest.getEmail())
                && userRepository.existsByEmail(updateUserRequest.getEmail())) {

            logger.warn("Email already exists: {}", user.getEmail());

            throw new BadRequestException(
                    "Email already exists: " + user.getEmail());
        }

// Update Entity
        UserMapper.updateEntity(user, updateUserRequest);

// Encrypt Password
//        user.setPassword(
//                passwordEncoder.encode(user.getPassword())
//        );

// Save Updated User
        User updatedUser = userRepository.save(user);

        logger.info("User updated successfully with ID: {}", updatedUser.getId());

        return UserMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {

        logger.info("Deleting user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {

                    logger.error("User not found with ID: {}", id);

                    return new ResourceNotFoundException(
                            "User not found with ID: " + id);
                });

        userRepository.delete(user);

        logger.info("User deleted successfully with ID: {}", id);
    }
}