package com.examcraft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Question text is required")
    @Column(nullable = false, length = 1000)
    private String questionText;

    @NotBlank(message = "Topic is required")
    @Column(nullable = false)
    private String topic;

    @NotNull(message = "Difficulty is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @NotNull(message = "Question type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType type;

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    @NotBlank(message = "Correct answer is required")
    @Column(nullable = false)
    private String correctAnswer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @Column(nullable = false)
    private Integer usageCount = 0;

    /*
     * Each question carries 1 mark by default.
     * This also fixes the database error:
     * "Field 'marks' doesn't have a default value"
     */
    @Column(nullable = false)
    private Integer marks = 1;

    @ManyToMany(mappedBy = "questions")
    @JsonIgnore
    private Set<TestPaper> testPapers = new HashSet<>();

    // Default constructor
    public Question() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public Set<TestPaper> getTestPapers() {
        return testPapers;
    }

    public void setTestPapers(Set<TestPaper> testPapers) {
        this.testPapers = testPapers;
    }
}