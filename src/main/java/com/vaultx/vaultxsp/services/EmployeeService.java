package com.vaultx.vaultxsp.services;

import com.vaultx.vaultxsp.dtos.EmployeeDto.*;

import java.util.List;

public interface EmployeeService {
    GetEmployeeProfileDto createEmployee(CreateEmployeeDto dto);
    List<GetEmployeeProfileDto> getAllEmployees();
    GetEmployeeProfileDto getEmployeeById(String employeeId);
    GetEmployeeProfileDto updateEmployee(String employeeId, UpdateEmployeeDto dto);
    void deleteEmployee(String employeeId);
}

