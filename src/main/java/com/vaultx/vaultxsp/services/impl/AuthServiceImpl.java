package com.vaultx.vaultxsp.services.impl;

import com.vaultx.vaultxsp.dtos.UserDto.*;
import com.vaultx.vaultxsp.models.User;
import com.vaultx.vaultxsp.repositories.UserRepository;
import com.vaultx.vaultxsp.security.JwtUtil;
import com.vaultx.vaultxsp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil         jwtUtil;

    @Override
    public LoginResponseDto login(LoginDto dto) {

        // 1. Find user by email
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        // 2. Check if blocked
        if (Boolean.TRUE.equals(user.getIsBlocked())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account is blocked");
        }

        // 3. Verify password
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        // 4. Generate JWT — role embedded inside token here
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return new LoginResponseDto(
                token,
                user.getUserid(),
                user.getEmail(),
                user.getRole(),
                user.getFirstname() != null ? user.getFirstname() : "",
                user.getLastname()  != null ? user.getLastname()  : ""
        );
    }
}

