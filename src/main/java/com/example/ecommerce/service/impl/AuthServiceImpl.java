package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.LoginRequestDto;
import com.example.ecommerce.dto.LoginResponseDto;
import com.example.ecommerce.service.AuthService;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequest) {
        // Fetch user by username (or email if needed)
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));
        // Validate password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        // Generate JWT with roles
        String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());
        return new LoginResponseDto(token);
    }
}
