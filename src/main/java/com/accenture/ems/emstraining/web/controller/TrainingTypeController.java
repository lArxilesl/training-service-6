package com.accenture.ems.emstraining.web.controller;

import com.accenture.ems.emstraining.business.service.TrainingTypeService;
import com.accenture.ems.emstraining.model.TrainingType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "Finds all training types",
            notes = "Returns the entire list of training types",
            response = TrainingTypeController.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded"),
            @ApiResponse(code = 401, message = "The request requires user authentication"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI"),
            @ApiResponse(code = 500, message = "Server error") })
    public ResponseEntity<List<TrainingType>> findAllTrainingTypes(){
        return ResponseEntity.ok(TrainingTypeService.getAll());
    }
    @ApiOperation(value = "Finds all training types by id",
            notes = "Returns the list of training types by id",
            response = TrainingTypeController.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded"),
            @ApiResponse(code = 401, message = "The request requires user authentication"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI"),
            @ApiResponse(code = 500, message = "Server error") })
    @GetMapping("/{Id}")
    public ResponseEntity<TrainingType> getTrainingType(@PathVariable Integer Id){
        return TrainingTypeService.getTrainingTypeById(Id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @ApiOperation(value = "Save all training type",
            notes = "Saves the entire training type",
            response = TrainingTypeController.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded"),
            @ApiResponse(code = 401, message = "The request requires user authentication"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI"),
            @ApiResponse(code = 500, message = "Server error") })    @PostMapping
    public ResponseEntity<Void> saveTrainingType(@Valid @RequestBody TrainingType TrainingType){
        TrainingTypeService.saveTrainingType(TrainingType);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @ApiOperation(value = "Updates the training type by id",
            notes = "Updates the entire training type by id",
            response = TrainingTypeController.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded"),
            @ApiResponse(code = 401, message = "The request requires user authentication"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI"),
            @ApiResponse(code = 500, message = "Server error") })
    @PutMapping("/{Id}")
    public ResponseEntity<TrainingType> updateTrainingType(@PathVariable Integer Id, @Valid @RequestBody TrainingType TrainingType){
        return ResponseEntity.ok(TrainingTypeService.updateTrainingType(Id,TrainingType));
    }
    @ApiOperation(value = "Deletes all training type by id",
            notes = "Deletes the entire list of training type by id",
            response = TrainingTypeController.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded"),
            @ApiResponse(code = 401, message = "The request requires user authentication"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI"),
            @ApiResponse(code = 500, message = "Server error") })
    @DeleteMapping("/{Id}")
    public ResponseEntity<Void> deleteTrainingType (@PathVariable Integer Id){
        TrainingTypeService.deleteTrainingType(Id);
        return ResponseEntity.noContent().build();
    }
}
