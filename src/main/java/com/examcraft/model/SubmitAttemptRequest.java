package com.examcraft.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public class SubmitAttemptRequest {
    @NotBlank(message = "Student name is required")
    private String studentName;

    @NotNull(message = "Test paper id is required")
    private Long testPaperId;

    @NotNull(message = "Answers are required")
    private Map<Long, String> answers;

    public SubmitAttemptRequest() {}
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public Long getTestPaperId() { return testPaperId; }
    public void setTestPaperId(Long testPaperId) { this.testPaperId = testPaperId; }
    public Map<Long, String> getAnswers() { return answers; }
    public void setAnswers(Map<Long, String> answers) { this.answers = answers; }
}
