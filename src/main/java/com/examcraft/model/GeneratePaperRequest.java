package com.examcraft.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class GeneratePaperRequest {
    @NotNull(message = "Total questions is required")
    @Positive(message = "Total questions must be greater than 0")
    private Integer totalQuestions;

    @NotNull(message = "Easy percentage is required")
    @Min(0) @Max(100)
    private Integer easyPercent;

    @NotNull(message = "Medium percentage is required")
    @Min(0) @Max(100)
    private Integer mediumPercent;

    @NotNull(message = "Hard percentage is required")
    @Min(0) @Max(100)
    private Integer hardPercent;

    private Long unitId;
    private String title;

    public GeneratePaperRequest() {}
    public Integer getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(Integer totalQuestions) { this.totalQuestions = totalQuestions; }
    public Integer getEasyPercent() { return easyPercent; }
    public void setEasyPercent(Integer easyPercent) { this.easyPercent = easyPercent; }
    public Integer getMediumPercent() { return mediumPercent; }
    public void setMediumPercent(Integer mediumPercent) { this.mediumPercent = mediumPercent; }
    public Integer getHardPercent() { return hardPercent; }
    public void setHardPercent(Integer hardPercent) { this.hardPercent = hardPercent; }
    public Long getUnitId() { return unitId; }
    public void setUnitId(Long unitId) { this.unitId = unitId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
