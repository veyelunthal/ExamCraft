package com.examcraft.controller;

import com.examcraft.model.Unit;
import com.examcraft.service.UnitService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/units")
@CrossOrigin
public class UnitController {
    private final UnitService unitService;
    public UnitController(UnitService unitService) { this.unitService = unitService; }

    @GetMapping public List<Unit> getAll() { return unitService.getAll(); }
    @GetMapping("/{id}") public Unit getById(@PathVariable Long id) { return unitService.getById(id); }

    @PostMapping
    public ResponseEntity<Unit> create(@Valid @RequestBody Unit unit) {
        return ResponseEntity.status(HttpStatus.CREATED).body(unitService.create(unit));
    }

    @PutMapping("/{id}")
    public Unit update(@PathVariable Long id, @Valid @RequestBody Unit unit) {
        return unitService.update(id, unit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        unitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
