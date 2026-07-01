package com.accenture.ems.emstraining.controller;

import com.accenture.ems.emstraining.model.entity.Training;
import com.accenture.ems.emstraining.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/training")
public class TrainingController {
    private final TrainingRepository trainingRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Training> getById(@PathVariable Long id) {
        return ResponseEntity.of(trainingRepository.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Training>> getAll() {
        return ResponseEntity.ok(trainingRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Training> create(@RequestBody Training training) {
        return ResponseEntity.ok(trainingRepository.save(training));
    }

    @PutMapping
    public ResponseEntity<Training> update(@RequestBody Training training) {
        return ResponseEntity.ok(trainingRepository.save(training));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Training> delete(@PathVariable Long id) {
        trainingRepository.deleteById(id);
        return ResponseEntity.noContent()
                             .build();
    }
}
