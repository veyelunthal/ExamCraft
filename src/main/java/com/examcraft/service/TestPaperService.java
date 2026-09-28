package com.examcraft.service;

import com.examcraft.model.*;
import com.examcraft.repository.QuestionRepository;
import com.examcraft.repository.TestPaperRepository;
import com.examcraft.repository.UnitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class TestPaperService {
    private final TestPaperRepository testPaperRepository;
    private final QuestionRepository questionRepository;
    private final UnitRepository unitRepository;

    public TestPaperService(TestPaperRepository testPaperRepository,
                            QuestionRepository questionRepository,
                            UnitRepository unitRepository) {
        this.testPaperRepository = testPaperRepository;
        this.questionRepository = questionRepository;
        this.unitRepository = unitRepository;
    }

    public List<TestPaper> getAll() { return testPaperRepository.findAll(); }

    public TestPaper getById(Long id) {
        return testPaperRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Test paper not found: " + id));
    }

    @Transactional
    public TestPaper generate(GeneratePaperRequest request) {
        int total = request.getTotalQuestions();
        int easy = request.getEasyPercent();
        int medium = request.getMediumPercent();
        int hard = request.getHardPercent();

        if (easy + medium + hard != 100)
            throw new IllegalArgumentException("Easy + Medium + Hard percentages must equal 100");

        Unit unit = null;
        if (request.getUnitId() != null)
            unit = unitRepository.findById(request.getUnitId())
                    .orElseThrow(() -> new IllegalArgumentException("Unit not found: " + request.getUnitId()));

        int[] target = calculateTargetCounts(total, easy, medium, hard);
        Map<Difficulty, Integer> targets = new EnumMap<>(Difficulty.class);
        targets.put(Difficulty.EASY, target[0]);
        targets.put(Difficulty.MEDIUM, target[1]);
        targets.put(Difficulty.HARD, target[2]);

        List<Question> selected = new ArrayList<>();
        Set<Long> selectedIds = new HashSet<>();

        for (Difficulty d : Difficulty.values()) {
            List<Question> pool = unit == null
                    ? questionRepository.findByDifficulty(d)
                    : questionRepository.findByUnitIdAndDifficulty(unit.getId(), d);
            Collections.shuffle(pool);
            int take = Math.min(targets.get(d), pool.size());
            for (int i = 0; i < take; i++) {
                Question q = pool.get(i);
                selected.add(q);
                selectedIds.add(q.getId());
            }
        }

        // Fill missing slots from other difficulties when the requested mix
        // cannot be met because the available question bank is unbalanced.
        if (selected.size() < total) {
            List<Question> fallback = unit == null
                    ? questionRepository.findAll()
                    : questionRepository.findByUnitId(unit.getId());
            Collections.shuffle(fallback);
            for (Question q : fallback) {
                if (selected.size() >= total) break;
                if (!selectedIds.contains(q.getId())) {
                    selected.add(q);
                    selectedIds.add(q.getId());
                }
            }
        }

        if (selected.size() < total)
            throw new IllegalArgumentException("Not enough unique questions. Requested "
                    + total + ", but only " + selected.size() + " are available.");

        Collections.shuffle(selected);

        TestPaper paper = new TestPaper();
        paper.setTitle(request.getTitle() == null || request.getTitle().isBlank()
                ? "Generated Exam" : request.getTitle().trim());
        paper.setTotalQuestions(total);
        paper.setEasyPercent(easy);
        paper.setMediumPercent(medium);
        paper.setHardPercent(hard);
        paper.setQuestions(selected);

        TestPaper saved = testPaperRepository.save(paper);

        for (Question q : selected)
            q.setUsageCount((q.getUsageCount() == null ? 0 : q.getUsageCount()) + 1);
        questionRepository.saveAll(selected);

        return saved;
    }

    @Transactional
    public void delete(Long id) {
        TestPaper paper = getById(id);
        if (!paper.getAttempts().isEmpty())
            throw new IllegalArgumentException("Cannot delete a test paper that has attempts");
        testPaperRepository.delete(paper);
    }

    private int[] calculateTargetCounts(int total, int easy, int medium, int hard) {
        double[] raw = { total * easy / 100.0, total * medium / 100.0, total * hard / 100.0 };
        int[] result = { (int)Math.floor(raw[0]), (int)Math.floor(raw[1]), (int)Math.floor(raw[2]) };
        int remaining = total - result[0] - result[1] - result[2];

        List<Integer> indexes = new ArrayList<>(List.of(0,1,2));
        indexes.sort((a,b) -> Double.compare(
                raw[b] - Math.floor(raw[b]), raw[a] - Math.floor(raw[a])));

        for (int i = 0; i < remaining; i++) result[indexes.get(i)]++;
        return result;
    }
}
