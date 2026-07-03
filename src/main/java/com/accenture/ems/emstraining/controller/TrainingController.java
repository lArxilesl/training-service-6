package com.accenture.ems.emstraining.controller;

import com.accenture.ems.emstraining.model.dto.TrainingPostDTO;
import com.accenture.ems.emstraining.model.dto.TrainingResponseDTO;
import com.accenture.ems.emstraining.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public ResponseEntity<TrainingResponseDTO> getById(@NotNull @PathVariable Long id) {
        log.info("getById(): retrieving training with id={}", id);

        Optional<TrainingResponseDTO> responseDTO = service.getById(id);
        if (!responseDTO.isPresent()) {
            log.info("getById(): training with id={} not found", id);
        } else {
            log.info("getById(): found training with id={}", responseDTO.get());
        }

        return ResponseEntity.of(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<TrainingResponseDTO>> getAll() {
        log.info("getAll(): retrieving all training entities");

        List<TrainingResponseDTO> responseDTOs = service.getAll();
        if (responseDTOs.isEmpty()) {
            log.info("getAll(): returning empty training list");
        } else {
            log.info("getAll(): returning training list with size={}", responseDTOs.size());
        }

        return ResponseEntity.ok(responseDTOs);
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
        log.info("update(): updating training with id={}", id);

        Optional<TrainingResponseDTO> responseDTO = service.updateById(id, postDTO);
        if (!responseDTO.isPresent()) {
            log.info("update(): training with id={} not found", id);
            return ResponseEntity
                    .notFound()
                    .build();
        }
        log.info("update(): updated training with id={}", responseDTO.get());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDTO.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TrainingResponseDTO> delete(@NotNull @PathVariable Long id) {
        log.info("delete(): deleting training with id={}", id);

        boolean success = service.deleteById(id);
        if (!success) {
            log.info("delete(): training with id={} not found", id);
            return ResponseEntity
                    .notFound()
                    .build();
        }

        log.info("delete(): deleted training with id={}", id);
        return ResponseEntity
                .noContent()
                .build();
    }
}