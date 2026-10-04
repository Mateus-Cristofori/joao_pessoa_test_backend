package com.example.demo.features.user.service.impl;

import com.example.demo.features.user.model.request.CreateUserRequest;
import com.example.demo.features.user.model.response.UserResponse;
import com.example.demo.features.user.repository.UserRepository;
import com.example.demo.features.user.repository.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Should create user successfully and encode password")
    void createUserSuccess() {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("Fulano");
        request.setEmail("fulano@gmail.com");
        request.setPassword("123456");

        UUID userId = UUID.randomUUID();
        String encodedPassword = "encodedPassword123";

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn(encodedPassword);

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User userArg = invocation.getArgument(0);
            return User.builder()
                .id(userId)
                .name(userArg.getName())
                .email(userArg.getEmail())
                .password(userArg.getPassword())
                .createdAt(LocalDateTime.now())
                .build();
        });

        UserResponse response = userService.createUser(request);

        assertNotNull(response);
        assertEquals(userId, response.getId());
        assertEquals("Fulano", response.getName());
        assertEquals("fulano@gmail.com", response.getEmail());

        verify(userRepository, times(1)).existsByEmail(request.getEmail());
        verify(passwordEncoder, times(1)).encode("123456");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when email already exists during creation")
    void createUserThrowsExceptionWhenEmailAlreadyExists() {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("fulano");
        request.setEmail("fulano@gmail.com");
        request.setPassword("123456");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            userService.createUser(request)
        );

        assertEquals("User with email already exists: fulano@gmail.com", exception.getMessage());

        verify(userRepository, times(1)).existsByEmail(request.getEmail());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should list all users successfully")
    void listAllUsersSuccess() {
        User user = User.builder()
            .id(UUID.randomUUID())
            .name("Fulano")
            .email("fulano@gmail.com")
            .password("encoded")
            .createdAt(LocalDateTime.now())
            .build();

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponse> responses = userService.listAllUsers();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Fulano", responses.getFirst().getName());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should retrieve user by ID successfully")
    void retrieveByIdSuccess() {
        UUID userId = UUID.randomUUID();
        User user = User.builder()
            .id(userId)
            .name("Fulano")
            .email("fulano@gmail.com")
            .password("encoded")
            .createdAt(LocalDateTime.now())
            .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserResponse response = userService.retrieveById(userId);

        assertNotNull(response);
        assertEquals(userId, response.getId());
        assertEquals("Fulano", response.getName());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when user ID does not exist")
    void retrieveByIdThrowsExceptionWhenUserNotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.retrieveById(userId));

        verify(userRepository, times(1)).findById(userId);
    }
}