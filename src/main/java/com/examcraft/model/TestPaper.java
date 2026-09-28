package com.examcraft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
<<<<<<< HEAD

=======
>>>>>>> a238c95fb3f7278d5e56cd439d867ad0b1022181
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "test_papers")
public class TestPaper {
<<<<<<< HEAD

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
=======
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
>>>>>>> a238c95fb3f7278d5e56cd439d867ad0b1022181
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer totalQuestions;

    @Column(nullable = false)
    private Integer easyPercent;

    @Column(nullable = false)
    private Integer mediumPercent;

    @Column(nullable = false)
    private Integer hardPercent;

<<<<<<< HEAD
    /*
     * Number of questions actually requested
     * for each difficulty level.
     *
     * Example:
     * Total = 5
     * Easy = 40%   -> 2
     * Medium = 30% -> 2
     * Hard = 30%   -> 1
     */
    @Column(name = "easy_requested", nullable = false)
    private Integer easyRequested;

    @Column(name = "medium_requested", nullable = false)
    private Integer mediumRequested;

    @Column(name = "hard_requested", nullable = false)
    private Integer hardRequested;

=======
>>>>>>> a238c95fb3f7278d5e56cd439d867ad0b1022181
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
<<<<<<< HEAD
    @JoinTable(
            name = "test_paper_questions",
            joinColumns = @JoinColumn(name = "test_paper_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
=======
    @JoinTable(name = "test_paper_questions",
            joinColumns = @JoinColumn(name = "test_paper_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
>>>>>>> a238c95fb3f7278d5e56cd439d867ad0b1022181
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "testPaper")
    @JsonIgnore
    private Set<Attempt> attempts = new HashSet<>();

<<<<<<< HEAD
    // Default constructor
    public TestPaper() {
    }

    // Automatically set creation date
    @PrePersist
    public void beforeSave() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Integer getEasyPercent() {
        return easyPercent;
    }

    public void setEasyPercent(Integer easyPercent) {
        this.easyPercent = easyPercent;
    }

    public Integer getMediumPercent() {
        return mediumPercent;
    }

    public void setMediumPercent(Integer mediumPercent) {
        this.mediumPercent = mediumPercent;
    }

    public Integer getHardPercent() {
        return hardPercent;
    }

    public void setHardPercent(Integer hardPercent) {
        this.hardPercent = hardPercent;
    }

    public Integer getEasyRequested() {
        return easyRequested;
    }

    public void setEasyRequested(Integer easyRequested) {
        this.easyRequested = easyRequested;
    }

    public Integer getMediumRequested() {
        return mediumRequested;
    }

    public void setMediumRequested(Integer mediumRequested) {
        this.mediumRequested = mediumRequested;
    }

    public Integer getHardRequested() {
        return hardRequested;
    }

    public void setHardRequested(Integer hardRequested) {
        this.hardRequested = hardRequested;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Set<Attempt> getAttempts() {
        return attempts;
    }

    public void setAttempts(Set<Attempt> attempts) {
        this.attempts = attempts;
    }
}
=======
    public TestPaper() {}

    @PrePersist
    public void beforeSave() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(Integer totalQuestions) { this.totalQuestions = totalQuestions; }
    public Integer getEasyPercent() { return easyPercent; }
    public void setEasyPercent(Integer easyPercent) { this.easyPercent = easyPercent; }
    public Integer getMediumPercent() { return mediumPercent; }
    public void setMediumPercent(Integer mediumPercent) { this.mediumPercent = mediumPercent; }
    public Integer getHardPercent() { return hardPercent; }
    public void setHardPercent(Integer hardPercent) { this.hardPercent = hardPercent; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
    public Set<Attempt> getAttempts() { return attempts; }
    public void setAttempts(Set<Attempt> attempts) { this.attempts = attempts; }
}
>>>>>>> a238c95fb3f7278d5e56cd439d867ad0b1022181
