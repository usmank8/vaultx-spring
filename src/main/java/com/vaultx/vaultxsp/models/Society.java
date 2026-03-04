package com.vaultx.vaultxsp.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "societies")
public class Society {
    @Id
    @Column(name = "society_id", nullable = false, length = 30)
    private String society;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "postalCode", length = 20)
    private String postalCode;

    @Column(name = "user_id", length = 255)
    private String userId;

    // One-to-One: Society -> User (admin)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userid", insertable = false, updatable = false)
    private User user;


}