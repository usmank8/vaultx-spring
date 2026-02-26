package com.vaultx.vaultxsp.employee;

import com.vaultx.vaultxsp.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @ColumnDefault("newsequentialid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "internalRole", nullable = false, length = 100)
    private String internalRole;

    @Column(name = "department", length = 100)
    private String department;

    @Column(name = "shift", length = 50)
    private String shift;

    @Column(name = "joiningDate")
    private Instant joiningDate;

    @ColumnDefault("getdate()")
    @Column(name = "createdAt", nullable = false)
    private Instant createdAt;

    @ColumnDefault("getdate()")
    @Column(name = "updatedAt", nullable = false)
    private Instant updatedAt;

    @Column(name = "userid", length = 255)
    private String userid;

    // Many-to-One: Employee -> User
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", referencedColumnName = "userid", insertable = false, updatable = false)
    private User user;

}