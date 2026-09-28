package com.examcraft.service;

import com.examcraft.model.*;
import com.examcraft.repository.QuestionRepository;
import com.examcraft.repository.TestPaperRepository;
import com.examcraft.repository.UnitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
<<<<<<< HEAD

=======
>>>>>>> a238c95fb3f7278d5e56cd439d867ad0b1022181
import java.util.*;

@Service
public class TestPaperService {
<<<<<<< HEAD

=======
>>>>>>> a238c95fb3f7278d5e56cd439d867ad0b1022181
    private final TestPaperRepository testPaperRepository;
    private final QuestionRepository questionRepository;
    private final UnitRepository unitRepository;

<<<<<<< HEAD
    public TestPaperService(
            TestPaperRepository testPaperRepository,
            QuestionRepository questionRepository,
            UnitRepository unitRepository) {

=======
    public TestPaperService(TestPaperRepository testPaperRepository,
                            QuestionRepository questionRepository,
                            UnitRepository unitRepository) {
>>>>>>> a238c95fb3f7278d5e56cd439d867ad0b1022181
        this.testPaperRepository = testPaperRepository;
        this.questionRepository = questionRepository;
        this.unitRepository = unitRepository;
    }

<<<<<<< HEAD
    // Get all test papers
    public List<TestPaper> getAll() {
        return testPaperRepository.findAll();
    }

    // Get test paper by ID
    public TestPaper getById(Long id) {

        return testPaperRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Test paper not found: " + id));
    }

    // Generate randomized test paper
    @Transactional
    public TestPaper generate(GeneratePaperRequest request) {

        // ------------------------------------------------
        // 1. Read request values
        // ------------------------------------------------

=======
    public List<TestPaper> getAll() { return testPaperRepository.findAll(); }

    public TestPaper getById(Long id) {
        return testPaperRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Test paper not found: " + id));
    }

    @Transactional
    public TestPaper generate(GeneratePaperRequest request) {
>>>>>>> a238c95fb3f7278d5e56cd439d867ad0b1022181
        int total = request.getTotalQuestions();
        int easy = request.getEasyPercent();
        int medium = request.getMediumPercent();
        int hard = request.getHardPercent();

<<<<<<< HEAD
        // ------------------------------------------------
        // 2. Validate input
        // ------------------------------------------------

        if (total <= 0) {
            throw new IllegalArgumentException(
                    "Total questions must be greater than 0");
        }

        if (easy < 0 || medium < 0 || hard < 0) {
            throw new IllegalArgumentException(
                    "Difficulty percentages cannot be negative");
        }

        if (easy + medium + hard != 100) {
            throw new IllegalArgumentException(
                    "Easy + Medium + Hard percentages must equal 100");
        }

        // ------------------------------------------------
        // 3. Find selected Unit
        // ------------------------------------------------

        Unit unit = null;

        if (request.getUnitId() != null) {

            unit = unitRepository.findById(
                            request.getUnitId())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Unit not found: "
                                            + request.getUnitId()));
        }

        // ------------------------------------------------
        // 4. Calculate number of questions needed
        // ------------------------------------------------

        int[] target =
                calculateTargetCounts(
                        total,
                        easy,
                        medium,
                        hard
                );

        int easyRequested = target[0];
        int mediumRequested = target[1];
        int hardRequested = target[2];

        // ------------------------------------------------
        // 5. Store targets in a map
        // ------------------------------------------------

        Map<Difficulty, Integer> targets =
                new EnumMap<>(Difficulty.class);

        targets.put(
                Difficulty.EASY,
                easyRequested
        );

        targets.put(
                Difficulty.MEDIUM,
                mediumRequested
        );

        targets.put(
                Difficulty.HARD,
                hardRequested
        );

        // ------------------------------------------------
        // 6. Select random questions
        // ------------------------------------------------

        List<Question> selected =
                new ArrayList<>();

        Set<Long> selectedIds =
                new HashSet<>();

        for (Difficulty difficulty :
                Difficulty.values()) {

            List<Question> pool;

            if (unit == null) {

                pool = questionRepository
                        .findByDifficulty(difficulty);

            } else {

                pool = questionRepository
                        .findByUnitIdAndDifficulty(
                                unit.getId(),
                                difficulty
                        );
            }

            // Randomize question order
            Collections.shuffle(pool);

            int required =
                    targets.getOrDefault(
                            difficulty,
                            0
                    );

            int take =
                    Math.min(
                            required,
                            pool.size()
                    );

            for (int i = 0; i < take; i++) {

                Question question =
                        pool.get(i);

                // Prevent duplicate questions
                if (!selectedIds.contains(
                        question.getId())) {

                    selected.add(question);

                    selectedIds.add(
                            question.getId());
                }
            }
        }

        // ------------------------------------------------
        // 7. Fill remaining slots
        // ------------------------------------------------

        /*
         * If the exact difficulty distribution cannot
         * be satisfied because the question bank does
         * not contain enough questions, use questions
         * from other difficulty levels.
         */

        if (selected.size() < total) {

            List<Question> fallback;

            if (unit == null) {

                fallback =
                        questionRepository.findAll();

            } else {

                fallback =
                        questionRepository
                                .findByUnitId(
                                        unit.getId());
            }

            // Randomize fallback questions
            Collections.shuffle(fallback);

            for (Question question : fallback) {

                if (selected.size() >= total) {
                    break;
                }

                if (!selectedIds.contains(
                        question.getId())) {

                    selected.add(question);

                    selectedIds.add(
                            question.getId());
=======
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
>>>>>>> a238c95fb3f7278d5e56cd439d867ad0b1022181
                }
            }
        }

<<<<<<< HEAD
        // ------------------------------------------------
        // 8. Check whether enough questions exist
        // ------------------------------------------------

        if (selected.size() < total) {

            throw new IllegalArgumentException(
                    "Not enough unique questions. Requested "
                            + total
                            + ", but only "
                            + selected.size()
                            + " are available."
            );
        }

        // ------------------------------------------------
        // 9. Randomize final paper
        // ------------------------------------------------

        Collections.shuffle(selected);

        // ------------------------------------------------
        // 10. Create TestPaper object
        // ------------------------------------------------

        TestPaper paper =
                new TestPaper();

        paper.setTitle(
                request.getTitle() == null ||
                        request.getTitle().isBlank()
                        ? "Generated Exam"
                        : request.getTitle().trim()
        );

        paper.setTotalQuestions(total);

        paper.setEasyPercent(easy);

        paper.setMediumPercent(medium);

        paper.setHardPercent(hard);

        /*
         * IMPORTANT FIX
         *
         * These three values were missing before.
         *
         * Database requires:
         * easy_requested
         * medium_requested
         * hard_requested
         */

        paper.setEasyRequested(
                easyRequested
        );

        paper.setMediumRequested(
                mediumRequested
        );

        paper.setHardRequested(
                hardRequested
        );

        paper.setQuestions(selected);

        // ------------------------------------------------
        // 11. Save TestPaper
        // ------------------------------------------------

        TestPaper saved =
                testPaperRepository.save(paper);

        // ------------------------------------------------
        // 12. Increase question usage count
        // ------------------------------------------------

        for (Question question : selected) {

            int currentUsage =
                    question.getUsageCount() == null
                            ? 0
                            : question.getUsageCount();

            question.setUsageCount(
                    currentUsage + 1
            );
        }

        questionRepository.saveAll(selected);

        // ------------------------------------------------
        // 13. Return generated paper
        // ------------------------------------------------

        return saved;
    }

    // Delete test paper
    @Transactional
    public void delete(Long id) {

        TestPaper paper =
                getById(id);

        if (!paper.getAttempts().isEmpty()) {

            throw new IllegalArgumentException(
                    "Cannot delete a test paper that has attempts"
            );
        }

        testPaperRepository.delete(paper);
    }

    // ------------------------------------------------
    // Calculate difficulty distribution
    // ------------------------------------------------

    private int[] calculateTargetCounts(
            int total,
            int easy,
            int medium,
            int hard) {

        /*
         * Example:
         *
         * Total = 5
         *
         * Easy   = 40%
         * Medium = 30%
         * Hard   = 30%
         *
         * Raw:
         *
         * Easy   = 2.0
         * Medium = 1.5
         * Hard   = 1.5
         *
         * Floor:
         *
         * Easy   = 2
         * Medium = 1
         * Hard   = 1
         *
         * Remaining = 1
         *
         * The remaining question goes to one of the
         * categories with the largest decimal part.
         *
         * Result:
         *
         * Easy   = 2
         * Medium = 2
         * Hard   = 1
         */

        double[] raw = {
                total * easy / 100.0,
                total * medium / 100.0,
                total * hard / 100.0
        };

        int[] result = {
                (int) Math.floor(raw[0]),
                (int) Math.floor(raw[1]),
                (int) Math.floor(raw[2])
        };

        int remaining =
                total
                        - result[0]
                        - result[1]
                        - result[2];

        List<Integer> indexes =
                new ArrayList<>(
                        List.of(0, 1, 2)
                );

        // Sort by largest decimal remainder
        indexes.sort(
                (a, b) ->
                        Double.compare(
                                raw[b]
                                        - Math.floor(raw[b]),
                                raw[a]
                                        - Math.floor(raw[a])
                        )
        );

        for (int i = 0;
             i < remaining;
             i++) {

            result[
                    indexes.get(i)
                    ]++;
        }

        return result;
    }
}
=======
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
>>>>>>> a238c95fb3f7278d5e56cd439d867ad0b1022181
