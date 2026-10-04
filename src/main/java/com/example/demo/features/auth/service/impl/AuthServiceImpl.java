package com.example.demo.features.auth.service.impl;

import com.example.demo.features.jwt.JwtService;
import com.example.demo.features.auth.model.request.LoginRequest;
import com.example.demo.features.auth.model.request.RefreshTokenRequest;
import com.example.demo.features.auth.model.response.AuthResponse;
import com.example.demo.features.auth.service.AuthService;
import com.example.demo.features.user.repository.UserRepository;
import com.example.demo.features.user.repository.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .filter(u -> passwordEncoder.matches(request.getPassword(), u.getPassword()))
            .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas")
            );
        return buildResponse(user);
    }

    @Override
    public AuthResponse refresh(RefreshTokenRequest request) {
        UUID userId = jwtService.extractUserId(request.getRefreshToken(), JwtService.REFRESH)
            .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token inválido ou expirado")
            );

        User user = userRepository.findById(userId)
            .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token inválido ou expirado")
            );
        return buildResponse(user);
    }

    private AuthResponse buildResponse(User user) {
        return AuthResponse.builder()
            .accessToken(jwtService.generateAccessToken(user))
            .refreshToken(jwtService.generateRefreshToken(user))
            .tokenType("Bearer")
            .expiresIn(jwtService.getAccessExpirationSeconds())
            .userId(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .build();
    }
}