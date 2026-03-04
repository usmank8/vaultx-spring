package com.vaultx.vaultxsp.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

public class EmployeeDto {

    @Getter
    @Setter
    public static class CreateEmployeeDto {

        @NotBlank(message = "Firstname is required")
        @Size(max = 100)
        private String firstname;

        @NotBlank(message = "Lastname is required")
        @Size(max = 100)
        private String lastname;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email address")
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        private String password;

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^[+\\d\\s\\-()]{7,20}$", message = "Invalid phone number")
        private String phone;

        @NotBlank(message = "CNIC is required")
        private String cnic;

        @NotBlank(message = "InternalRole is required")
        @Size(max = 100)
        private String internalRole;

        private String department;

        private String shift;

        private LocalDate joiningDate;
    }

    @Getter
    @Setter
    public static class GetEmployeeProfileDto {

        private String employeeId;
        private String firstname = "";
        private String lastname = "";
        private String email;
        private String phone;
        private String cnic;
        private String internalRole;
        private String department;
        private String shift;
        private LocalDate joiningDate;
    }
}

