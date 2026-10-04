package com.example.demo.features.auth.service;

import com.example.demo.features.auth.model.request.LoginRequest;
import com.example.demo.features.auth.model.request.RefreshTokenRequest;
import com.example.demo.features.auth.model.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse refresh(RefreshTokenRequest request);
}
