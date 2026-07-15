package com.accenture.ems.emstraining.controller;

import com.accenture.ems.emstraining.model.TrainingDetailsDTO;
import com.accenture.ems.emstraining.service.TrainingDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;
import java.util.Optional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Training Details")
@RestController
@RequestMapping("/training-details")
@RequiredArgsConstructor
@Slf4j
public class TrainingDetailsController {

    private final TrainingDetailsService trainingDetailsService;

    @ApiOperation("Get training details by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Training details are found"),
            @ApiResponse(code = 404, message = "Training details aren't found")
    })
    @GetMapping("/{trainingDetailsId}")
    public ResponseEntity<TrainingDetailsDTO> getTrainingDetailsById(@PathVariable Long trainingDetailsId){
        log.info("Received request to get training details with id {}", trainingDetailsId);
        Optional<TrainingDetailsDTO> trainingDetailsDTO = trainingDetailsService.getTrainingDetailsById(trainingDetailsId);

        if(!trainingDetailsDTO.isPresent()){
            log.warn("Training details with id {} was not found", trainingDetailsId);
            return ResponseEntity.notFound().build();
        }
        log.info("Successfully found training details with id {}", trainingDetailsId);
        return ResponseEntity.ok(trainingDetailsDTO.get());
    }

    @ApiOperation("Get all training details")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Training details returned successfully")
    })
    @GetMapping
    public ResponseEntity<List<TrainingDetailsDTO>> getAllTrainingDetails(){
        log.info("Received request to get all training details");
        List<TrainingDetailsDTO> trainingDetailsDTOsList = trainingDetailsService.getAllTrainingDetails();
        log.info("Found {} training details", trainingDetailsDTOsList.size());

        return ResponseEntity.ok(trainingDetailsDTOsList);
    }

    @ApiOperation("Create training details")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Training details created"),
            @ApiResponse(code = 400, message = "Invalid request")
    })
    @PostMapping
    public ResponseEntity<TrainingDetailsDTO> createTrainingDetails(@Valid @RequestBody TrainingDetailsDTO trainingDetailsDTO){
        log.info("Received request to create training details");
        TrainingDetailsDTO newTrainingDetails = trainingDetailsService.createTrainingDetails(trainingDetailsDTO);
        log.info("Training details created with id: {}", newTrainingDetails.getId());

        return ResponseEntity.status(201).body(newTrainingDetails);
    }

    @ApiOperation("Update training details")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Training details updated"),
            @ApiResponse(code = 404, message = "Training details not found")
    })
    @PutMapping("/{trainingDetailsId}")
    public ResponseEntity<TrainingDetailsDTO> updateTrainingDetails(@PathVariable Long trainingDetailsId, @Valid @RequestBody TrainingDetailsDTO trainingDetailsDTO){
        log.info("Received request to update training details with id: {}", trainingDetailsId);
        TrainingDetailsDTO updatedTrainingDetails = trainingDetailsService.updateTrainingDetails(trainingDetailsId, trainingDetailsDTO);
        log.info("Training details with id {} updated", trainingDetailsId);

        return ResponseEntity.ok(updatedTrainingDetails);
    }

    @ApiOperation("Delete training details")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Training details deleted"),
            @ApiResponse(code = 404, message = "Training details not found")
    })
    @DeleteMapping("/{trainingDetailsId}")
    public ResponseEntity<TrainingDetailsDTO> deleteTrainingDetails(@PathVariable Long trainingDetailsId) {
        log.info("Received request to delete training details with id: {}", trainingDetailsId);
        trainingDetailsService.deleteTrainingDetails(trainingDetailsId);
        log.info("Training details with id {} deleted", trainingDetailsId);

        return ResponseEntity.noContent().build();
    }
}