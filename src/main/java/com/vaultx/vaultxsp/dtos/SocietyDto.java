package com.vaultx.vaultxsp.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

public class SocietyDto {

    @Getter
    @Setter
    public static class CreateSocietyDto {

        @NotBlank
        @Size(max = 100)
        private String name;

        @NotBlank
        @Size(max = 200)
        private String address;

        @Size(max = 100)
        private String city;

        @Size(max = 100)
        private String state;

        @Size(max = 20)
        private String postalCode;
    }

    @Getter
    @Setter
    public static class UpdateSocietyDto {

        @Size(max = 100)
        private String name;

        @Size(max = 200)
        private String address;

        @Size(max = 100)
        private String city;

        @Size(max = 100)
        private String state;

        @Size(max = 20)
        private String postalCode;
    }

    @Getter
    @Setter
    public static class SocietyResponseDto {

        private String societyId;
        private String name;
        private String address;
        private String city;
        private String state;
        private String postalCode;
    }

    @Getter
    @Setter
    public static class DashboardDataDto {

        private String todaysDate;
        private int totalUsers;
        private int totalGuests;
        private int totalVehicles;
        private int pendingResidents;
        private int approvedResidents;
    }
}

