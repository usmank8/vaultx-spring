package com.vaultx.vaultxsp.dtos;

import lombok.*;
import java.util.UUID;

public class ResidenceDto {

    public enum ResidenceEnum {
        APARTMENT,
        FLAT,
        HOUSE
    }

    public enum ResidenceType {
        RENTED,
        OWNED
    }

    @Getter
    @Setter
    public static class ResidenceSummaryDto {

        private String addressLine1;
        private String block;
        private String residence;
        private String residenceType;
    }

    @Getter
    @Setter
    public static class AddResidenceDto {

        private String residence;
        private String residenceType;
        private String block;
        private String address;
    }

    @Getter
    @Setter
    public static class ResidenceResponseDto {

        private String addressLine1;
        private String block;
        private String residence;
        private String residenceType;
    }

    @Getter
    @Setter
    public static class ResidentByStatusDto {

        private UUID residenceId;
        private String userId;
        private String firstname;
        private String lastname;
        private String cnic;
        private String email;
        private String phone;
        private String addressLine1;
        private String block;
        private String residence;
        private String residenceType;
        private String flatNumber;
        private boolean isPrimary;
    }
}

