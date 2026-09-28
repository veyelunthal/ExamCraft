package com.examcraft.controller;

import com.examcraft.model.*;
import com.examcraft.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin
public class QuestionController {
    private final QuestionService questionService;
    public QuestionController(QuestionService questionService) { this.questionService = questionService; }

    @GetMapping
    public List<Question> getAll(@RequestParam(required=false) Long unitId,
                                 @RequestParam(required=false) Difficulty difficulty) {
        return questionService.filter(unitId, difficulty);
    }

    @GetMapping("/{id}") public Question getById(@PathVariable Long id) { return questionService.getById(id); }

    @PostMapping
    public ResponseEntity<Question> create(@Valid @RequestBody Question question) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.create(question));
    }

    @PutMapping("/{id}")
    public Question update(@PathVariable Long id, @Valid @RequestBody Question question) {
        return questionService.update(id, question);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usage/frequency")
    public List<Question> usageFrequency() { return questionService.getUsageFrequency(); }
}
