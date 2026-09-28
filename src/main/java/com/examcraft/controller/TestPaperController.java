package com.examcraft.controller;

import com.examcraft.model.*;
import com.examcraft.service.TestPaperService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/testpapers")
@CrossOrigin
public class TestPaperController {
    private final TestPaperService testPaperService;
    public TestPaperController(TestPaperService testPaperService) { this.testPaperService = testPaperService; }

    @GetMapping public List<TestPaper> getAll() { return testPaperService.getAll(); }
    @GetMapping("/{id}") public TestPaper getById(@PathVariable Long id) { return testPaperService.getById(id); }

    @PostMapping("/generate")
    public ResponseEntity<TestPaper> generate(@Valid @RequestBody GeneratePaperRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(testPaperService.generate(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        testPaperService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
