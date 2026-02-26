package com.vaultx.vaultxsp.vehicle;

import com.vaultx.vaultxsp.guest.Guest;
import com.vaultx.vaultxsp.residence.Residence;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @Nationalized
    @Column(name = "vehicleId", nullable = false)
    private String vehicleId;

    @Nationalized
    @Column(name = "vehicleType", nullable = false)
    private String vehicleType;

    @Nationalized
    @Column(name = "vehicleModel", nullable = false)
    private String vehicleModel;

    @Nationalized
    @Column(name = "vehicleName", nullable = false)
    private String vehicleName;

    @Nationalized
    @Column(name = "vehicleLicensePlateNumber", nullable = false)
    private String vehicleLicensePlateNumber;

    @Nationalized
    @Column(name = "vehicleRFIDTagId", nullable = false)
    private String vehicleRFIDTagId;

    @Column(name = "isGuest", nullable = false)
    private Boolean isGuest;

    @ColumnDefault("getdate()")
    @Column(name = "createdAt", nullable = false)
    private Instant createdAt;

    @ColumnDefault("getdate()")
    @Column(name = "updatedAt", nullable = false)
    private Instant updatedAt;

    @Column(name = "residentid")
    private java.util.UUID residentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "residentid", referencedColumnName = "id", insertable = false, updatable = false)
    private Residence resident;

    // One-to-One: Vehicle -> Guest
    @OneToOne(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private Guest guest;

    @Nationalized
    @Column(name = "vehicleColor")
    private String vehicleColor;


}