package com.example.ecommerce.service;

import com.example.ecommerce.dto.LoginRequestDto;
import com.example.ecommerce.dto.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto loginRequest);
    // Add registration and JWT utility methods as needed
}

