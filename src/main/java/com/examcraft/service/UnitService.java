package com.examcraft.service;

import com.examcraft.model.Unit;
import com.examcraft.repository.UnitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UnitService {
    private final UnitRepository unitRepository;
    public UnitService(UnitRepository unitRepository) { this.unitRepository = unitRepository; }

    public List<Unit> getAll() { return unitRepository.findAll(); }

    public Unit getById(Long id) {
        return unitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unit not found: " + id));
    }

    public Unit create(Unit unit) {
        String name = unit.getName().trim();
        if (unitRepository.existsByNameIgnoreCase(name))
            throw new IllegalArgumentException("A unit with this name already exists");
        unit.setName(name);
        return unitRepository.save(unit);
    }

    public Unit update(Long id, Unit input) {
        Unit existing = getById(id);
        String name = input.getName().trim();
        if (!existing.getName().equalsIgnoreCase(name) && unitRepository.existsByNameIgnoreCase(name))
            throw new IllegalArgumentException("A unit with this name already exists");
        existing.setName(name);
        existing.setDescription(input.getDescription());
        return unitRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Unit existing = getById(id);
        if (!existing.getQuestions().isEmpty())
            throw new IllegalArgumentException("Cannot delete a unit that has questions");
        unitRepository.delete(existing);
    }
}
