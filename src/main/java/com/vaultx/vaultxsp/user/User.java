package com.vaultx.vaultxsp.user;

import com.vaultx.vaultxsp.employee.Employee;
import com.vaultx.vaultxsp.guest.Guest;
import com.vaultx.vaultxsp.otp.Otp;
import com.vaultx.vaultxsp.residence.Residence;
import com.vaultx.vaultxsp.society.Society;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @Nationalized
    @Column(name = "userid", nullable = false)
    private String userid;

    @Nationalized
    @Column(name = "email", nullable = false)
    private String email;

    @Nationalized
    @Column(name = "password", nullable = false)
    private String password;

    @Nationalized
    @Column(name = "firstname")
    private String firstname;

    @Nationalized
    @Column(name = "lastname")
    private String lastname;

    @Nationalized
    @Column(name = "cnic", length = 15)
    private String cnic;

    @ColumnDefault("CONVERT([bit], 0)")
    @Column(name = "isVerified")
    private Boolean isVerified;

    @ColumnDefault("CONVERT([bit], 0)")
    @Column(name = "isBlocked")
    private Boolean isBlocked;

    @ColumnDefault("getdate()")
    @Column(name = "createdAt", nullable = false)
    private Instant createdAt;

    @ColumnDefault("getdate()")
    @Column(name = "updatedAt", nullable = false)
    private Instant updatedAt;

    @Nationalized
    @ColumnDefault("N'resident'")
    @Column(name = "role", nullable = false)
    private String role;

    @Nationalized
    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "isEmailVerified", nullable = false)
    private Boolean isEmailVerified;

    // One-to-One: User -> Employee
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Employee employee;

    // One-to-Many: User -> Residences
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Residence> residences = new ArrayList<>();

    // One-to-Many: User -> Guests (invited by)
    @OneToMany(mappedBy = "invitedBy", cascade = CascadeType.ALL)
    private List<Guest> guests = new ArrayList<>();

    // One-to-One: User -> Otp
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Otp otp;

    // One-to-One: User -> Society (admin)
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Society society;


}