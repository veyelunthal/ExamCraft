package com.examcraft.controller;

import com.examcraft.model.*;
import com.examcraft.service.AttemptService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/attempts")
@CrossOrigin
public class AttemptController {
    private final AttemptService attemptService;
    public AttemptController(AttemptService attemptService) { this.attemptService = attemptService; }

    @GetMapping public List<Attempt> getAll() { return attemptService.getAll(); }
    @GetMapping("/{id}") public Attempt getById(@PathVariable Long id) { return attemptService.getById(id); }

    @PostMapping("/submit")
    public ResponseEntity<Attempt> submit(@Valid @RequestBody SubmitAttemptRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attemptService.submit(request));
    }
}
