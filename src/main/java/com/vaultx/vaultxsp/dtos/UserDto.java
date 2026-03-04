package com.vaultx.vaultxsp.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

public class UserDto {

    @Getter
    @Setter
    public static class RegisterUserDto {

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email address")
        @Size(max = 255, message = "Email must be at most 255 characters")
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters")
        private String password;
    }

    @Getter
    @Setter
    public static class ChangePasswordDto {

        @NotBlank
        private String newPassword;

        @NotBlank
        private String email;
    }

    @Getter
    @Setter
    public static class LoginDto {

        private String email;
        private String password;
    }
}
