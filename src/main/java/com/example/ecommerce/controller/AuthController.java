package com.example.ecommerce.controller;

import com.example.ecommerce.dto.LoginRequestDto;
import com.example.ecommerce.dto.LoginResponseDto;
import com.example.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Authentication API", description = "Endpoints for user authentication and JWT issuance")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(
        summary = "Authenticate user and issue JWT",
        description = "Authenticates the user with username and password, and returns a JWT token if successful.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Authentication successful, JWT returned", content = @Content(schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
        }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        content = @Content(schema = @Schema(implementation = LoginRequestDto.class))
    )
    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        // AuthService will authenticate and return JWT token in response DTO
        LoginResponseDto response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
    // Add registration endpoint if needed
}