package com.accenture.ems.emstraining.controller;

import com.accenture.ems.emstraining.model.dto.TrainingPostDTO;
import com.accenture.ems.emstraining.model.dto.TrainingResponseDTO;
import com.accenture.ems.emstraining.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/training")
public class TrainingController {
    private final TrainingService service;

    @GetMapping("/{id}")
    public ResponseEntity<TrainingResponseDTO> getById(@NotNull @PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<TrainingResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<TrainingResponseDTO> create(@Valid @RequestBody TrainingPostDTO postDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(postDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingResponseDTO> update(@NotNull @PathVariable Long id,
                                                      @Valid @RequestBody TrainingPostDTO postDTO) {
        Optional<TrainingResponseDTO> responseDTO = service.updateById(id, postDTO);
        if (!responseDTO.isPresent()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDTO.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TrainingResponseDTO> delete(@NotNull @PathVariable Long id) {
        boolean success = service.deleteById(id);

        if (!success) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity
                .noContent()
                .build();
    }
}