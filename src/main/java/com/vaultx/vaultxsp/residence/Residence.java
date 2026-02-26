package com.vaultx.vaultxsp.residence;

import com.vaultx.vaultxsp.guest.Guest;
import com.vaultx.vaultxsp.user.User;
import com.vaultx.vaultxsp.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "residences")
public class Residence {
    @Id
    @ColumnDefault("newsequentialid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Nationalized
    @Column(name = "addressLine1")
    private String addressLine1;

    @Nationalized
    @Column(name = "addressLine2")
    private String addressLine2;

    @ColumnDefault("'owned'")
    @Column(name = "residenceType", nullable = false, length = 20)
    private String residenceType;

    @ColumnDefault("'flat'")
    @Column(name = "residence", nullable = false, length = 20)
    private String residence;

    @Column(name = "isPrimary", nullable = false)
    private Boolean isPrimary;

    @Column(name = "isApprovedBySociety", nullable = false)
    private Boolean isApprovedBySociety;

    @Nationalized
    @Column(name = "approvedBy")
    private String approvedBy;

    @Nationalized
    @Column(name = "flatNumber")
    private String flatNumber;

    @Nationalized
    @Column(name = "block")
    private String block;

    @Column(name = "approvedAt")
    private Instant approvedAt;

    @ColumnDefault("getdate()")
    @Column(name = "createdAt", nullable = false)
    private Instant createdAt;

    @ColumnDefault("getdate()")
    @Column(name = "updatedAt", nullable = false)
    private Instant updatedAt;

    @Column(name = "userid", length = 255)
    private String userid;

    // Many-to-One: Residence -> User (resident)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", referencedColumnName = "userid", insertable = false, updatable = false)
    private User user;

    // One-to-Many: Residence -> Guests
    @OneToMany(mappedBy = "residence", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Guest> guests = new ArrayList<>();

    // One-to-Many: Residence -> Vehicles
    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> vehicles = new ArrayList<>();


}