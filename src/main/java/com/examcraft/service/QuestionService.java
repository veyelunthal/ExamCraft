package com.examcraft.service;

import com.examcraft.model.*;
import com.examcraft.repository.QuestionRepository;
import com.examcraft.repository.UnitRepository;
import org.springframework.stereotype.Service;
<<<<<<< HEAD

=======
>>>>>>> a238c95fb3f7278d5e56cd439d867ad0b1022181
import java.util.List;

@Service
public class QuestionService {
<<<<<<< HEAD

    private final QuestionRepository questionRepository;
    private final UnitRepository unitRepository;

    public QuestionService(
            QuestionRepository questionRepository,
            UnitRepository unitRepository) {

=======
    private final QuestionRepository questionRepository;
    private final UnitRepository unitRepository;

    public QuestionService(QuestionRepository questionRepository, UnitRepository unitRepository) {
>>>>>>> a238c95fb3f7278d5e56cd439d867ad0b1022181
        this.questionRepository = questionRepository;
        this.unitRepository = unitRepository;
    }

<<<<<<< HEAD
    // Get all questions
    public List<Question> getAll() {
        return questionRepository.findAll();
    }

    // Get questions ordered by usage frequency
=======
    public List<Question> getAll() { return questionRepository.findAll(); }

>>>>>>> a238c95fb3f7278d5e56cd439d867ad0b1022181
    public List<Question> getUsageFrequency() {
        return questionRepository.findAllByOrderByUsageCountDesc();
    }

<<<<<<< HEAD
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
=======
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
>>>>>>> a238c95fb3f7278d5e56cd439d867ad0b1022181
        existing.setQuestionText(input.getQuestionText());
        existing.setTopic(input.getTopic());
        existing.setDifficulty(input.getDifficulty());
        existing.setType(input.getType());
        existing.setOptionA(input.getOptionA());
        existing.setOptionB(input.getOptionB());
        existing.setOptionC(input.getOptionC());
        existing.setOptionD(input.getOptionD());
<<<<<<< HEAD

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
=======
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
>>>>>>> a238c95fb3f7278d5e56cd439d867ad0b1022181
