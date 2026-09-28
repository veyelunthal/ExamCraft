package com.examcraft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "test_papers")
public class TestPaper {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "test_paper_questions",
            joinColumns = @JoinColumn(name = "test_paper_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "testPaper")
    @JsonIgnore
    private Set<Attempt> attempts = new HashSet<>();

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
