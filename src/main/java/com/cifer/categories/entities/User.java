package com.cifer.categories.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity
 * sessionId - stored sessionId
 * firstName - user firstName
 * sectors - save user sectors
 */
@Entity
@Table(name = "USERS")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_SESSION_ID")
    private String sessionId;

    @Column(name = "USER_FIRST_NAME")
    private String firstName;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "USER_SECTORS",
            joinColumns = { @JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "SECTOR_ID")})
    private Set<Sector> sectors = new HashSet<>();

}
