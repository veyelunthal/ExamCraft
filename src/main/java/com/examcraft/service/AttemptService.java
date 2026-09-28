package com.examcraft.service;

import com.examcraft.model.*;
import com.examcraft.repository.AttemptRepository;
import com.examcraft.repository.TestPaperRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AttemptService {
    private final AttemptRepository attemptRepository;
    private final TestPaperRepository testPaperRepository;

    public AttemptService(AttemptRepository attemptRepository, TestPaperRepository testPaperRepository) {
        this.attemptRepository = attemptRepository;
        this.testPaperRepository = testPaperRepository;
    }

    public List<Attempt> getAll() { return attemptRepository.findAllByOrderBySubmittedAtDesc(); }

    public Attempt getById(Long id) {
        return attemptRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Attempt not found: " + id));
    }

    @Transactional
    public Attempt submit(SubmitAttemptRequest request) {
        TestPaper paper = testPaperRepository.findById(request.getTestPaperId())
                .orElseThrow(() -> new IllegalArgumentException("Test paper not found: " + request.getTestPaperId()));

        int score = 0;
        for (Question q : paper.getQuestions()) {
            String submitted = request.getAnswers().get(q.getId());
            if (submitted != null && q.getCorrectAnswer() != null
                    && q.getCorrectAnswer().trim().equalsIgnoreCase(submitted.trim())) score++;
        }

        Attempt attempt = new Attempt();
        attempt.setStudentName(request.getStudentName().trim());
        attempt.setTestPaper(paper);
        attempt.setScore(score);
        attempt.setTotalMarks(paper.getQuestions().size());
        return attemptRepository.save(attempt);
    }
}
