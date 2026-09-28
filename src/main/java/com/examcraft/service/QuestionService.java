package com.examcraft.service;

import com.examcraft.model.*;
import com.examcraft.repository.QuestionRepository;
import com.examcraft.repository.UnitRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UnitRepository unitRepository;

    public QuestionService(QuestionRepository questionRepository, UnitRepository unitRepository) {
        this.questionRepository = questionRepository;
        this.unitRepository = unitRepository;
    }

    public List<Question> getAll() { return questionRepository.findAll(); }

    public List<Question> getUsageFrequency() {
        return questionRepository.findAllByOrderByUsageCountDesc();
    }

    public Question getById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Question not found: " + id));
    }

    public Question create(Question question) {
        validateQuestion(question);
        Unit unit = unitRepository.findById(question.getUnit().getId())
                .orElseThrow(() -> new IllegalArgumentException("Unit not found"));
        question.setUnit(unit);
        question.setUsageCount(0);
        question.setCorrectAnswer(question.getCorrectAnswer().trim().toUpperCase());
        return questionRepository.save(question);
    }

    public Question update(Long id, Question input) {
        validateQuestion(input);
        Question existing = getById(id);
        Unit unit = unitRepository.findById(input.getUnit().getId())
                .orElseThrow(() -> new IllegalArgumentException("Unit not found"));
        existing.setQuestionText(input.getQuestionText());
        existing.setTopic(input.getTopic());
        existing.setDifficulty(input.getDifficulty());
        existing.setType(input.getType());
        existing.setOptionA(input.getOptionA());
        existing.setOptionB(input.getOptionB());
        existing.setOptionC(input.getOptionC());
        existing.setOptionD(input.getOptionD());
        existing.setCorrectAnswer(input.getCorrectAnswer().trim().toUpperCase());
        existing.setUnit(unit);
        return questionRepository.save(existing);
    }

    public void delete(Long id) { questionRepository.delete(getById(id)); }

    public List<Question> filter(Long unitId, Difficulty difficulty) {
        if (unitId != null && difficulty != null)
            return questionRepository.findByUnitIdAndDifficulty(unitId, difficulty);
        if (unitId != null) return questionRepository.findByUnitId(unitId);
        if (difficulty != null) return questionRepository.findByDifficulty(difficulty);
        return questionRepository.findAll();
    }

    private void validateQuestion(Question q) {
        if (q.getUnit() == null || q.getUnit().getId() == null)
            throw new IllegalArgumentException("Unit is required");

        if (q.getType() == QuestionType.MCQ) {
            if (blank(q.getOptionA()) || blank(q.getOptionB()) || blank(q.getOptionC()) || blank(q.getOptionD()))
                throw new IllegalArgumentException("MCQ requires options A, B, C and D");
            String a = q.getCorrectAnswer() == null ? "" : q.getCorrectAnswer().trim().toUpperCase();
            if (!List.of("A", "B", "C", "D").contains(a))
                throw new IllegalArgumentException("For MCQ, correctAnswer must be A, B, C or D");
        }

        if (q.getType() == QuestionType.TRUE_FALSE) {
            String a = q.getCorrectAnswer() == null ? "" : q.getCorrectAnswer().trim().toUpperCase();
            if (!List.of("TRUE", "FALSE").contains(a))
                throw new IllegalArgumentException("For TRUE_FALSE, correctAnswer must be TRUE or FALSE");
        }
    }

    private boolean blank(String value) { return value == null || value.trim().isEmpty(); }
}
