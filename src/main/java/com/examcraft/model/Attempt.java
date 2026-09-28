package com.examcraft.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attempts")
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Maps Java field studentName to database column student_name
    @Column(name = "student_name", nullable = false)
    private String studentName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "test_paper_id", nullable = false)
    private TestPaper testPaper;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "total_marks", nullable = false)
    private Integer totalMarks;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    public Attempt() {
    }

    @PrePersist
    public void beforeSave() {
        if (submittedAt == null) {
            submittedAt = LocalDateTime.now();
        }
    }

    // ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Student Name
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    // Test Paper
    public TestPaper getTestPaper() {
        return testPaper;
    }

    public void setTestPaper(TestPaper testPaper) {
        this.testPaper = testPaper;
    }

    // Score
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    // Total Marks
    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    // Submitted At
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}