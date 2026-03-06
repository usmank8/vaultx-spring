package com.vaultx.vaultxsp.services.impl;

import com.vaultx.vaultxsp.dtos.EmployeeDto.*;
import com.vaultx.vaultxsp.models.Employee;
import com.vaultx.vaultxsp.models.User;
import com.vaultx.vaultxsp.repositories.EmployeeRepository;
import com.vaultx.vaultxsp.repositories.UserRepository;
import com.vaultx.vaultxsp.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository     userRepository;
    private final PasswordEncoder    passwordEncoder;

    // ── CREATE ───────────────────────────────────────────────────────────────
    // Mirrors .NET: Step 1 — create User, Step 2 — create Employee linked by userId
    @Override
    @Transactional
    public GetEmployeeProfileDto createEmployee(CreateEmployeeDto dto) {

        // Validate — same as .NET: check email OR cnic already exists
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        if (userRepository.existsByCnic(dto.getCnic())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CNIC already exists");
        }

        // Step 1 — CREATE USER RECORD (mirrors .NET userId = "usr_" + Guid.NewGuid())
        String userId = "usr_" + UUID.randomUUID().toString().replace("-", "");

        User user = new User();
        user.setUserid(userId);
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // BCrypt
        user.setPhone(dto.getPhone());
        user.setCnic(dto.getCnic());
        user.setRole("employee");
        user.setIsVerified(true);
        user.setIsEmailVerified(true);
        user.setIsBlocked(false);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        userRepository.save(user);

        // Step 2 — CREATE EMPLOYEE RECORD (linked to user via userid)
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setUserid(userId);
        employee.setInternalRole(dto.getInternalRole());
        employee.setDepartment(dto.getDepartment());
        employee.setShift(dto.getShift());
        employee.setJoiningDate(
                dto.getJoiningDate() != null
                        ? dto.getJoiningDate().atStartOfDay(ZoneOffset.UTC).toInstant()
                        : Instant.now()
        );
        employee.setCreatedAt(Instant.now());
        employee.setUpdatedAt(Instant.now());
        employeeRepository.save(employee);

        return toDto(employee, user);
    }

    // ── GET ALL ──────────────────────────────────────────────────────────────
    // Mirrors .NET: _context.Employees.Include(e => e.User).ToListAsync()
    @Override
    public List<GetEmployeeProfileDto> getAllEmployees() {
        return employeeRepository.findAllWithUser()
                .stream()
                .map(e -> toDto(e, e.getUser()))
                .collect(Collectors.toList());
    }

    // ── GET BY ID ────────────────────────────────────────────────────────────
    // Mirrors .NET: FirstOrDefaultAsync(e => e.Id.ToString() == employeeId)
    @Override
    public GetEmployeeProfileDto getEmployeeById(String employeeId) {
        Employee employee = resolveEmployee(employeeId);
        return toDto(employee, employee.getUser());
    }

    // ── UPDATE BY ID ─────────────────────────────────────────────────────────
    // Partial update — only fields provided in the DTO are changed
    @Override
    @Transactional
    public GetEmployeeProfileDto updateEmployee(String employeeId, UpdateEmployeeDto dto) {
        Employee employee = resolveEmployee(employeeId);
        User user = employee.getUser();

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Linked user record not found");
        }

        // Update User fields (only if provided)
        if (dto.getFirstname() != null)  user.setFirstname(dto.getFirstname());
        if (dto.getLastname() != null)   user.setLastname(dto.getLastname());
        if (dto.getPhone() != null)      user.setPhone(dto.getPhone());
        if (dto.getCnic() != null)       user.setCnic(dto.getCnic());
        if (dto.getEmail() != null) {
            // Ensure new email isn't taken by a different user
            if (userRepository.existsByEmail(dto.getEmail())
                    && !dto.getEmail().equalsIgnoreCase(user.getEmail())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
            }
            user.setEmail(dto.getEmail());
        }
        user.setUpdatedAt(Instant.now());
        userRepository.save(user);

        // Update Employee fields (only if provided)
        if (dto.getInternalRole() != null) employee.setInternalRole(dto.getInternalRole());
        if (dto.getDepartment() != null)   employee.setDepartment(dto.getDepartment());
        if (dto.getShift() != null)        employee.setShift(dto.getShift());
        if (dto.getJoiningDate() != null) {
            employee.setJoiningDate(
                    dto.getJoiningDate().atStartOfDay(ZoneOffset.UTC).toInstant()
            );
        }
        employee.setUpdatedAt(Instant.now());
        employeeRepository.save(employee);

        return toDto(employee, user);
    }

    // ── DELETE BY ID ─────────────────────────────────────────────────────────
    // Deletes Employee first, then User — User.employee has orphanRemoval so
    // deleting the User would also cascade, but we delete both explicitly for clarity
    @Override
    @Transactional
    public void deleteEmployee(String employeeId) {
        Employee employee = resolveEmployee(employeeId);
        User user = employee.getUser();

        employeeRepository.delete(employee);

        if (user != null) {
            userRepository.delete(user);
        }
    }

    // ── PRIVATE HELPERS ──────────────────────────────────────────────────────

    private Employee resolveEmployee(String employeeId) {
        try {
            UUID uuid = UUID.fromString(employeeId);
            return employeeRepository.findByIdWithUser(uuid)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Employee not found"));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid employee ID format");
        }
    }

    // ── MAPPER: Employee + User → GetEmployeeProfileDto ──────────────────────
    private GetEmployeeProfileDto toDto(Employee e, User u) {
        GetEmployeeProfileDto dto = new GetEmployeeProfileDto();
        dto.setEmployeeId(e.getId() != null ? e.getId().toString() : null);
        dto.setFirstname(u != null && u.getFirstname() != null ? u.getFirstname() : "");
        dto.setLastname(u != null && u.getLastname()  != null ? u.getLastname()  : "");
        dto.setEmail(u != null ? u.getEmail() : null);
        dto.setPhone(u != null ? u.getPhone() : null);
        dto.setCnic(u  != null ? u.getCnic()  : null);
        dto.setInternalRole(e.getInternalRole());
        dto.setDepartment(e.getDepartment());
        dto.setShift(e.getShift());
        dto.setJoiningDate(
                e.getJoiningDate() != null
                        ? e.getJoiningDate().atZone(ZoneOffset.UTC).toLocalDate()
                        : null
        );
        return dto;
    }
}

