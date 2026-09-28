package com.examcraft.repository;

import com.examcraft.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    boolean existsByNameIgnoreCase(String name);
}
