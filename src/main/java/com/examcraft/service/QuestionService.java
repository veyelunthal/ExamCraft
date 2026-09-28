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

    public QuestionService(
            QuestionRepository questionRepository,
            UnitRepository unitRepository) {

        this.questionRepository = questionRepository;
        this.unitRepository = unitRepository;
    }

    // Get all questions
    public List<Question> getAll() {
        return questionRepository.findAll();
    }

    // Get questions ordered by usage frequency
    public List<Question> getUsageFrequency() {
        return questionRepository.findAllByOrderByUsageCountDesc();
    }

    // Get question by ID
    public Question getById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Question not found: " + id));
    }

    // Create a new question
    public Question create(Question question) {

        // Validate question
        validateQuestion(question);

        // Make sure Unit exists
        Unit unit = unitRepository.findById(
                        question.getUnit().getId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unit not found"));

        question.setUnit(unit);

        // New question starts with usage count 0
        question.setUsageCount(0);

        /*
         * If frontend does not send marks,
         * automatically assign 1 mark.
         */
        if (question.getMarks() == null) {
            question.setMarks(1);
        }

        // Store correct answer in uppercase
        question.setCorrectAnswer(
                question.getCorrectAnswer()
                        .trim()
                        .toUpperCase()
        );

        return questionRepository.save(question);
    }

    // Update an existing question
    public Question update(Long id, Question input) {

        // Validate input
        validateQuestion(input);

        // Find existing question
        Question existing = getById(id);

        // Make sure Unit exists
        Unit unit = unitRepository.findById(
                        input.getUnit().getId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unit not found"));

        // Update fields
        existing.setQuestionText(input.getQuestionText());
        existing.setTopic(input.getTopic());
        existing.setDifficulty(input.getDifficulty());
        existing.setType(input.getType());
        existing.setOptionA(input.getOptionA());
        existing.setOptionB(input.getOptionB());
        existing.setOptionC(input.getOptionC());
        existing.setOptionD(input.getOptionD());

        existing.setCorrectAnswer(
                input.getCorrectAnswer()
                        .trim()
                        .toUpperCase()
        );

        existing.setUnit(unit);

        /*
         * If marks are not provided during update,
         * keep the existing marks.
         */
        if (input.getMarks() != null) {
            existing.setMarks(input.getMarks());
        }

        return questionRepository.save(existing);
    }

    // Delete question
    public void delete(Long id) {
        questionRepository.delete(getById(id));
    }

    // Filter questions
    public List<Question> filter(
            Long unitId,
            Difficulty difficulty) {

        if (unitId != null && difficulty != null) {

            return questionRepository
                    .findByUnitIdAndDifficulty(
                            unitId,
                            difficulty
                    );
        }

        if (unitId != null) {

            return questionRepository
                    .findByUnitId(unitId);
        }

        if (difficulty != null) {

            return questionRepository
                    .findByDifficulty(difficulty);
        }

        return questionRepository.findAll();
    }

    // Validate question
    private void validateQuestion(Question q) {

        // Check Unit
        if (q.getUnit() == null ||
                q.getUnit().getId() == null) {

            throw new IllegalArgumentException(
                    "Unit is required");
        }

        // MCQ validation
        if (q.getType() == QuestionType.MCQ) {

            if (blank(q.getOptionA()) ||
                    blank(q.getOptionB()) ||
                    blank(q.getOptionC()) ||
                    blank(q.getOptionD())) {

                throw new IllegalArgumentException(
                        "MCQ requires options A, B, C and D");
            }

            String answer =
                    q.getCorrectAnswer() == null
                            ? ""
                            : q.getCorrectAnswer()
                            .trim()
                            .toUpperCase();

            if (!List.of("A", "B", "C", "D")
                    .contains(answer)) {

                throw new IllegalArgumentException(
                        "For MCQ, correctAnswer must be A, B, C or D");
            }
        }

        // True / False validation
        if (q.getType() == QuestionType.TRUE_FALSE) {

            String answer =
                    q.getCorrectAnswer() == null
                            ? ""
                            : q.getCorrectAnswer()
                            .trim()
                            .toUpperCase();

            if (!List.of("TRUE", "FALSE")
                    .contains(answer)) {

                throw new IllegalArgumentException(
                        "For TRUE_FALSE, correctAnswer must be TRUE or FALSE");
            }
        }
    }

    // Check empty string
    private boolean blank(String value) {

        return value == null ||
                value.trim().isEmpty();
    }
}