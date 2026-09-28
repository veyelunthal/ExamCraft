package com.examcraft.repository;

import com.examcraft.model.Difficulty;
import com.examcraft.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByDifficulty(Difficulty difficulty);
    List<Question> findByUnitId(Long unitId);
    List<Question> findByUnitIdAndDifficulty(Long unitId, Difficulty difficulty);
    List<Question> findAllByOrderByUsageCountDesc();
}
