package com.vaultx.vaultxsp.dtos;

import com.vaultx.vaultxsp.dtos.ResidenceDto.ResidenceSummaryDto;
import lombok.*;

public class ApprovalDto {

    @Getter
    @Setter
    public static class PendingApprovalDto {

        private String residentId;
        private String firstname;
        private String lastname;
        private String cnic;
        private String email;
        private String phone;
        private ResidenceSummaryDto residence;
    }

    @Getter
    @Setter
    public static class VerifyResponse {

        private boolean valid;
        private String reason;
    }

    @Getter
    @Setter
    public static class ApprovalResponse {

        private String message;
    }
}

