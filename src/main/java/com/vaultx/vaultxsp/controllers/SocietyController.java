package com.vaultx.vaultxsp.controllers;

import com.vaultx.vaultxsp.dtos.SocietyDto.*;
import com.vaultx.vaultxsp.services.SocietyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/society")
@RequiredArgsConstructor
@Tag(name = "Society")
public class SocietyController {

    private final SocietyService societyService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SocietyResponseDto> addSociety(
            @Valid @RequestBody CreateSocietyDto dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(societyService.createSociety(dto, userDetails.getUsername()));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<SocietyResponseDto> getSociety() {
        return ResponseEntity.ok(societyService.getSociety());
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SocietyResponseDto> updateSociety(
            @Valid @RequestBody UpdateSocietyDto dto) {
        return ResponseEntity.ok(societyService.updateSociety(dto));
    }
}
