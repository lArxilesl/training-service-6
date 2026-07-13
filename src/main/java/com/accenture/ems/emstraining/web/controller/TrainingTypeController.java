package com.accenture.ems.emstraining.web.controller;

import com.accenture.ems.emstraining.business.service.TrainingTypeService;
import com.accenture.ems.emstraining.model.TrainingType;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/training")
@Slf4j
public class TrainingTypeController {
    private final TrainingTypeService TrainingTypeService;

    public TrainingTypeController(TrainingTypeService TrainingTypeService) {
        this.TrainingTypeService = TrainingTypeService;
    }
    @Operation(summary = "Find all TrainingTypes")
    @GetMapping
    public ResponseEntity<List<TrainingType>> findAllTrainingTypes(){
        return ResponseEntity.ok(TrainingTypeService.getAll());
    }
    @Operation(summary = "Get TrainingTypes By Id")
    @GetMapping("/{Id}")
    public ResponseEntity<TrainingType> getTrainingType(@PathVariable Integer Id){
        return TrainingTypeService.getTrainingTypeById(Id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Operation(summary = "Save TrainingTypes")
    @PostMapping
    public ResponseEntity<Void> saveTrainingType(@Valid @RequestBody TrainingType TrainingType){
        TrainingTypeService.saveTrainingType(TrainingType);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(summary = "Update TrainingTypes By Id")
    @PutMapping("/{Id}")
    public ResponseEntity<TrainingType> updateTrainingType(@PathVariable Integer Id, @Valid @RequestBody TrainingType TrainingType){
        return ResponseEntity.ok(TrainingTypeService.updateTrainingType(Id,TrainingType));
    }
    @Operation(summary = "Delete TrainingTypes By Id")
    @DeleteMapping("/{Id}")
    public ResponseEntity<Void> deleteTrainingType (@PathVariable Integer Id){
        TrainingTypeService.deleteTrainingType(Id);
        return ResponseEntity.noContent().build();
    }
}
