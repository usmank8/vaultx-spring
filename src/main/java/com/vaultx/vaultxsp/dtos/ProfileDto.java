package com.vaultx.vaultxsp.dtos;

import com.vaultx.vaultxsp.dtos.ResidenceDto.ResidenceEnum;
import com.vaultx.vaultxsp.dtos.ResidenceDto.ResidenceType;
import com.vaultx.vaultxsp.dtos.ResidenceDto.ResidenceResponseDto;
import jakarta.validation.constraints.*;
import lombok.*;

public class ProfileDto {

    @Getter
    @Setter
    public static class CreateProfileDto {

        @NotBlank(message = "Firstname is required")
        @Size(max = 100, message = "Firstname must be at most 100 characters")
        private String firstname;

        @NotBlank(message = "Lastname is required")
        @Size(max = 100, message = "Lastname must be at most 100 characters")
        private String lastname;

        @NotBlank(message = "Phonenumber is required")
        @Pattern(regexp = "^[+\\d\\s\\-()]{7,20}$", message = "Invalid phone number")
        private String phonenumber;

        @NotBlank(message = "CNIC is required")
        @Size(min = 13, max = 13, message = "CNIC must be exactly 13 digits")
        @Pattern(regexp = "^\\d{13}$", message = "CNIC must be 13 digits")
        private String cnic;

        @NotNull(message = "Residence is required")
        private ResidenceEnum residence;

        @NotNull(message = "ResidenceType is required")
        private ResidenceType residenceType;

        @NotBlank(message = "Block is required")
        @Size(max = 50, message = "Block must be at most 50 characters")
        private String block;

        @NotBlank(message = "Address is required")
        @Size(max = 500, message = "Address must be at most 500 characters")
        private String address;
    }

    @Getter
    @Setter
    public static class UpdateProfileDto {

        private String firstname;
        private String lastname;
        private String phonenumber;
        private String cnic;
        private ResidenceEnum residence;
        private ResidenceType residenceType;
        private String block;
        private String address;
    }

    @Getter
    @Setter
    public static class GetUserProfileDto {

        private String firstname = "";
        private String lastname = "";
        private String phone = "";
        private String cnic = "";
        private String email = "";
        private ResidenceResponseDto residence;
    }

    @Getter
    @Setter
    public static class UpdatePasswordDto {

        @NotBlank(message = "Current password is required")
        @Size(min = 6, message = "Current password must be at least 6 characters")
        private String currentPassword;

        @NotBlank(message = "New password is required")
        @Size(min = 6, message = "New password must be at least 6 characters")
        private String newPassword;
    }
}

