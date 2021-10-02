package com.cifer.categories.repos;

import com.cifer.categories.entities.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * Repository for sectors
 */
public interface SectorRepository extends JpaRepository<Sector, Long> {
    Set<Sector> findByValueIn(List<String> values);
}
