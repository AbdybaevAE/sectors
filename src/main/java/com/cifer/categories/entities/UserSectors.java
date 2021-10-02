package com.cifer.categories.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * UsersSectors entity which defines pair of userId and sectorId
 */
@Entity
@Data
public class UserSectors {
    @Id
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "SECTOR_ID")
    private Long sectorId;
}
