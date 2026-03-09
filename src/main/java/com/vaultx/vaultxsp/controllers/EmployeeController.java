package com.vaultx.vaultxsp.controllers;

import com.vaultx.vaultxsp.dtos.EmployeeDto.*;
import com.vaultx.vaultxsp.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    // ── POST /api/employees/create ───────────────────────────────────────────
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create employee", description = "Admin only. Creates a User + Employee record.")
    public ResponseEntity<GetEmployeeProfileDto> createEmployee(
            @Valid @RequestBody CreateEmployeeDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(dto));
    }

    // ── GET /api/employees/all ───────────────────────────────────────────────
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @Operation(summary = "Get all employees", description = "Admin and Employee roles.")
    public ResponseEntity<List<GetEmployeeProfileDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // ── GET /api/employees/profile/{employeeId} ──────────────────────────────
    @GetMapping("/profile/{employeeId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @Operation(summary = "Get employee by ID")
    public ResponseEntity<GetEmployeeProfileDto> getEmployeeById(
            @PathVariable String employeeId) {
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }

    // ── PATCH /api/employees/update/{employeeId} ─────────────────────────────
    @PatchMapping("/update/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update employee", description = "Admin only. Only provided fields are updated.")
    public ResponseEntity<GetEmployeeProfileDto> updateEmployee(
            @PathVariable String employeeId,
            @Valid @RequestBody UpdateEmployeeDto dto) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, dto));
    }

    // ── DELETE /api/employees/delete/{employeeId} ────────────────────────────
    @DeleteMapping("/delete/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete employee", description = "Admin only. Also deletes the linked User record.")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }
}

