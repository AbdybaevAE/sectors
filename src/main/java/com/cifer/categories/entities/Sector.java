package com.cifer.categories.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * Sector entity class
 * value - sector unique value
 * description - human readable description
 * parentValue - sector's parent value
 * order - order of sector in sibling relations
 */
@Entity
@Table(name = "SECTORS")
@Data
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SECTOR_ID")
    private Long id;

    @Column(name = "SECTOR_VALUE", nullable = false)
    private String value;

    @Column(name = "SECTOR_DESCRIPTION")
    private String description;

    @Column(name = "SECTOR_PARENT_VALUE")
    private String parentValue;

    @Column(name = "SECTOR_ORDER")
    private Integer order;
}
