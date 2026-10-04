package com.example.demo.features.user.service.impl;

import com.example.demo.features.user.model.request.CreateUserRequest;
import com.example.demo.features.user.model.response.UserResponse;
import com.example.demo.features.user.repository.UserRepository;
import com.example.demo.features.user.repository.entity.User;
import com.example.demo.features.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        log.info("Creating new user with email: {}", createUserRequest.getEmail());

        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new IllegalArgumentException("User with email already exists: " + createUserRequest.getEmail());
        }

        User user = userRepository.save(
            User.builder()
                .name(createUserRequest.getName())
                .email(createUserRequest.getEmail())
                .createdAt(LocalDateTime.now())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .build()
        );

        log.info("User created successfully with ID: {}", user.getId());
        return mapToResponse(user);
    }

    @Override
    public List<UserResponse> listAllUsers() {
        log.info("Listing all system users");
        return userRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Override
    public UserResponse retrieveById(UUID userId) {
        log.info("Retrieving user by ID: {}", userId);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        return mapToResponse(user);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .createdAt(user.getCreatedAt())
            .build();
    }
}
