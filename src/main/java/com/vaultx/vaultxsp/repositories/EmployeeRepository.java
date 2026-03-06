package com.vaultx.vaultxsp.repositories;

import com.vaultx.vaultxsp.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    // Used for getEmployeeById — matches the .NET FirstOrDefault(e => e.Id.ToString() == employeeId)
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.user WHERE e.id = :id")
    Optional<Employee> findByIdWithUser(@Param("id") UUID id);

    // Used for getAllEmployees — eager-loads the linked User in one query (avoids N+1)
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.user")
    java.util.List<Employee> findAllWithUser();
}

