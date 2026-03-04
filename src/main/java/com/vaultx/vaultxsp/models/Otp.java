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
@Table(name = "otp")
public class Otp {
    @Id
    @ColumnDefault("newsequentialid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Nationalized
    @Column(name = "code", nullable = false, length = 6)
    private String code;

    @Column(name = "expiresAt", nullable = false)
    private Instant expiresAt;

    @ColumnDefault("getdate()")
    @Column(name = "createdAt", nullable = false)
    private Instant createdAt;

    @Column(name = "isUsed", nullable = false)
    private Boolean isUsed;

    @Column(name = "userUserid", length = 255)
    private String userUserid;

    // One-to-One: Otp -> User
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userUserid", referencedColumnName = "userid", insertable = false, updatable = false)
    private User user;


}