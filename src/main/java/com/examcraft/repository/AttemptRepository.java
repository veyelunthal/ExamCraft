package com.examcraft.repository;

import com.examcraft.model.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    List<Attempt> findAllByOrderBySubmittedAtDesc();
}
