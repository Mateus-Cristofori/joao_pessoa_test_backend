package com.example.demo.features.user.service;

import com.example.demo.features.user.model.request.CreateUserRequest;
import com.example.demo.features.user.model.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
    List<UserResponse> listAllUsers();
    UserResponse retrieveById(UUID userId);
}
