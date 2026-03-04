package com.vaultx.vaultxsp.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

public class VehicleDto {

    @Getter
    @Setter
    public static class AddVehicleDto {

        @NotBlank
        private String vehicleName;

        private String vehicleModel;

        @NotBlank
        private String vehicleType;

        @NotBlank
        private String vehicleLicensePlateNumber;

        private String vehicleRfidTagId;

        private String vehicleColor;

        // Optional: Specify which residence (defaults to primary if not provided)
        private UUID residenceId;
    }

    @Getter
    @Setter
    public static class UpdateVehicleDto {

        private String vehicleName;
        private String vehicleModel;
        private String vehicleType;
        private String vehicleLicensePlateNumber;
        private String vehicleRfidTagId;
        private String vehicleColor;
    }

    @Getter
    @Setter
    public static class AddGuestVehicleDto {

        private UUID residenceId;
        private String vehicleName;
        private String vehicleModel;
        private String vehicleType;
        private String vehicleLicensePlateNumber;
        private String vehicleRFIDTagId;
        private String vehicleColor;
    }

    @Getter
    @Setter
    public static class VehicleResponseDto {

        private String vehicleId;
        private String ownerName;
        private String vehicleName;
        private String vehicleModel;
        private String vehicleType;
        private String vehicleLicensePlateNumber;
        private String vehicleRFIDTagId;
        private String vehicleColor;
        private boolean isGuest;
    }
}

