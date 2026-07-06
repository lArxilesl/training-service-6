package com.accenture.ems.emstraining.controller;

import com.accenture.ems.emstraining.model.dto.TrainingPostDTO;
import com.accenture.ems.emstraining.model.dto.TrainingResponseDTO;
import com.accenture.ems.emstraining.service.TrainingService;
import com.accenture.ems.emstraining.swagger.DescriptionVariables;
import com.accenture.ems.emstraining.swagger.HTMLResponseMessages;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/training")
@Slf4j
@Api(tags = DescriptionVariables.TRAINING)
public class TrainingController {
    private final TrainingService service;

    @ApiOperation("Get Training By Id")
    @ApiResponses({@ApiResponse(code = 200, message = HTMLResponseMessages.HTTP_200, response =
            TrainingResponseDTO.class), @ApiResponse(code = 404, message = HTMLResponseMessages.HTTP_404),})
    @ResponseStatus(HttpStatus.OK)
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

    @ApiOperation("Get all Trainings")
    @ApiResponses({@ApiResponse(code = 200, message = HTMLResponseMessages.HTTP_200, response =
            TrainingResponseDTO.class, responseContainer = "List"),})
    @ResponseStatus(HttpStatus.OK)
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

    @ApiOperation("Create Training")
    @ApiResponses({@ApiResponse(code = 201, message = HTMLResponseMessages.HTTP_201, response =
            TrainingResponseDTO.class), @ApiResponse(code = 400, message = HTMLResponseMessages.HTTP_400),})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<TrainingResponseDTO> create(@Valid @RequestBody TrainingPostDTO postDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(postDTO));
    }

    @ApiOperation(value = "Update Training by Id")
    @ApiResponses({@ApiResponse(code = 201, message = HTMLResponseMessages.HTTP_201, response =
            TrainingResponseDTO.class), @ApiResponse(code = 400, message = HTMLResponseMessages.HTTP_400),
            @ApiResponse(code = 404, message = HTMLResponseMessages.HTTP_404),})
    @ResponseStatus(HttpStatus.CREATED)
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

    @ApiOperation("Delete Training by Id")
    @ApiResponses({@ApiResponse(code = 204, message = HTMLResponseMessages.HTTP_204_WITHOUT_DATA), @ApiResponse(code
            = 400, message = HTMLResponseMessages.HTTP_400), @ApiResponse(code = 404, message =
            HTMLResponseMessages.HTTP_404)})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
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