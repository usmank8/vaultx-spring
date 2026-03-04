package com.vaultx.vaultxsp.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class GuestDto {

    @Getter
    @Setter
    public static class AddGuestDto {

        private String guestName;
        private String guestPhoneNumber;
        private String gender;
        private LocalDateTime eta;
        private LocalDateTime checkoutTime;
        private UUID residenceId;

        // Vehicle details — optional, auto-created if provided
        private String vehicleName;
        private String vehicleModel;
        private String vehicleLicensePlateNumber;
        private String vehicleType;
        private String vehicleColor;
    }

    @Getter
    @Setter
    public static class ExtendGuestTimeDto {

        @NotNull(message = "New checkout time is required")
        private LocalDateTime newCheckoutTime;
    }

    @Getter
    @Setter
    public static class GuestWithVehicleDto {

        private String guestId;
        private String guestName;
        private LocalDateTime eta;
        private String vehicleId;
        private String vehicleModel;
        private String vehicleLicensePlateNumber;
        private String vehicleColor;
        private Boolean isGuest;
    }

    @Getter
    @Setter
    public static class GuestResidenceDto {

        private String id;
        private String addressLine1;
        private String addressLine2;
        private String city;
        private String state;
        private String country;
        private String postalCode;
        private String flatNumber;
        private String block;
    }

    @Getter
    @Setter
    public static class GuestVehicleDto {

        private String vehicleId;
        private String vehicleType;
        private String vehicleModel;
        private String vehicleName;
        private String vehicleLicensePlateNumber;
        private String vehicleRFIDTagId;
        private String vehicleColor;
        private boolean isGuest;
    }

    @Getter
    @Setter
    public static class GuestDetailDto {

        private String guestId;
        private String guestName;
        private String guestPhoneNumber;
        private LocalDateTime eta;
        private boolean visitCompleted;
        private GuestResidenceDto residence;
        private GuestVehicleDto guestVehicle;
    }

    @Getter
    @Setter
    public static class VerifyGuestDto {

        @NotBlank
        private String guestId;
    }

    @Getter
    @Setter
    public static class QrCodeResponse {

        private String qrCodeImage;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaginatedGuestsDto {

        private List<GuestDetailDto> data;
        private int total;
        private int skip;
        private int limit;
        private int page;
    }
}

