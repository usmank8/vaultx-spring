package com.vaultx.vaultxsp.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "vehicle_access_logs")
public class VehicleAccessLog {
    @Id
    @ColumnDefault("newsequentialid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Nationalized
    @Column(name = "accessType", nullable = false, length = 10)
    private String accessType;

    @Column(name = "\"timestamp\"", nullable = false)
    private Instant timestamp;

    @Nationalized
    @Column(name = "gateName", length = 100)
    private String gateName;

    @Nationalized
    @Column(name = "recordedBy")
    private String recordedBy;

    @Column(name = "vehicleId", length = 255)
    private String vehicleId;

    // Many-to-One: VehicleAccessLog -> Vehicle
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicleId", referencedColumnName = "vehicleId", insertable = false, updatable = false)
    private Vehicle vehicle;


}