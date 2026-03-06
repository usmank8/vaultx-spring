package com.vaultx.vaultxsp.services;

import com.vaultx.vaultxsp.dtos.UserDto.*;

public interface AuthService {
    LoginResponseDto login(LoginDto dto);
}

