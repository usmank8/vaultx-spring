package com.vaultx.vaultxsp.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

public class OtpDto {

    @Getter
    @Setter
    public static class OtpRequestDto {

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email address")
        private String email;

        @NotBlank(message = "OTP is required")
        @Size(min = 6, max = 6, message = "OTP must be exactly 6 digits")
        @Pattern(regexp = "^\\d{6}$", message = "OTP must be 6 digits")
        private String otp;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OtpResponseDto {

        private boolean success;
        private String message;
        private String error;
    }
}
