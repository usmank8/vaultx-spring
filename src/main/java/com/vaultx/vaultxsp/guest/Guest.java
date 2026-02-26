package com.vaultx.vaultxsp.guest;

import com.vaultx.vaultxsp.residence.Residence;
import com.vaultx.vaultxsp.user.User;
import com.vaultx.vaultxsp.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "guests")
public class Guest {
    @Id
    @Nationalized
    @Column(name = "guestId", nullable = false)
    private String guestId;

    @Nationalized
    @Column(name = "guestName", nullable = false)
    private String guestName;

    @Nationalized
    @Column(name = "guestPhoneNumber", nullable = false)
    private String guestPhoneNumber;

    @Nationalized
    @Column(name = "Gender", nullable = false, length = 20)
    private String gender;

    @Column(name = "eta", nullable = false)
    private Instant eta;

    @Column(name = "CheckoutTime", nullable = false)
    private Instant checkoutTime;

    @Column(name = "ActualArrivalTime")
    private Instant actualArrivalTime;

    @Nationalized
    @Column(name = "Status", nullable = false, length = 20)
    private String status;

    @Column(name = "visitCompleted", nullable = false)
    private Boolean visitCompleted;

    @Column(name = "isVerified", nullable = false)
    private Boolean isVerified;

    @Column(name = "createdAt", nullable = false)
    private Instant createdAt;

    @Column(name = "updatedAt", nullable = false)
    private Instant updatedAt;

    @Column(name = "qrCode", nullable = false)
    private byte[] qrCode;

    // Foreign Keys
    @Column(name = "userid", length = 255)
    private String userid;

    @Column(name = "residenceId")
    private java.util.UUID residenceId;

    @Column(name = "vehicleId", length = 255)
    private String vehicleId;

    // Many-to-One: Guest -> User (invited by)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", referencedColumnName = "userid", insertable = false, updatable = false)
    private User invitedBy;

    // Many-to-One: Guest -> Residence (visiting)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residenceId", referencedColumnName = "id", insertable = false, updatable = false)
    private Residence residence;

    // One-to-One: Guest -> Vehicle
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicleId", referencedColumnName = "vehicleId", insertable = false, updatable = false)
    private Vehicle vehicle;


}