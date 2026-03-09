package com.vaultx.vaultxsp.controllers;

import com.vaultx.vaultxsp.dtos.UserDto.*;
import com.vaultx.vaultxsp.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth")
public class AuthController {

    private final AuthService authService;

    // POST /api/auth/login
    // Public — no JWT required. Returns a token on success.
    @PostMapping("/login")
    @Operation(summary = "Login", description = "Returns a JWT token. Use it in the Authorize button above.")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}

