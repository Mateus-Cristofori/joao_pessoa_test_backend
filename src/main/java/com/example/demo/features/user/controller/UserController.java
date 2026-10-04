package com.example.demo.features.user.controller;

import com.example.demo.features.user.model.request.CreateUserRequest;
import com.example.demo.features.user.model.response.UserResponse;
import com.example.demo.features.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Valid CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping
    public List<UserResponse> listAllUsers() {
        return userService.listAllUsers();
    }

    @GetMapping("/{userId}")
    public UserResponse retrieveById(@PathVariable UUID userId) {
        return userService.retrieveById(userId);
    }
}
